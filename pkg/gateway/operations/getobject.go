package operations

import (
	"errors"
	"fmt"
	"io"
	"net/http"
	"time"

	"github.com/treeverse/lakefs/pkg/block"
	"github.com/treeverse/lakefs/pkg/catalog"
	gatewayerrors "github.com/treeverse/lakefs/pkg/gateway/errors"
	"github.com/treeverse/lakefs/pkg/gateway/serde"
	"github.com/treeverse/lakefs/pkg/graveler"
	"github.com/treeverse/lakefs/pkg/httputil"
	"github.com/treeverse/lakefs/pkg/permissions"
)

type GetObject struct{}

func (controller *GetObject) RequiredPermissions(_ *http.Request, repoID, _, path string) (permissions.Node, error) {
	return permissions.Node{
		Permission: permissions.Permission{
			Action:   permissions.ReadObjectAction,
			Resource: permissions.ObjectArn(repoID, path),
		},
	}, nil
}

func (controller *GetObject) Handle(w http.ResponseWriter, req *http.Request, o *PathOperation) {
	o.Incr("get_object", o.Principal, o.Repository.Name, o.Reference)
	ctx := req.Context()
	query := req.URL.Query()
	if _, exists := query["versioning"]; exists {
		o.EncodeResponse(w, req, serde.VersioningConfiguration{}, http.StatusOK)
		return
	}

	if _, exists := query["tagging"]; exists {
		o.EncodeResponse(w, req, serde.Tagging{}, http.StatusOK)
		return
	}

	beforeMeta := time.Now()
	entry, err := o.Catalog.GetEntry(ctx, o.Repository.Name, o.Reference, o.Path, catalog.GetEntryParams{})
	metaTook := time.Since(beforeMeta)
	o.Log(req).
		WithField("took", metaTook).
		WithError(err).
		Debug("metadata operation to retrieve object done")

	if errors.Is(err, graveler.ErrNotFound) {
		// TODO: create distinction between missing repo & missing key
		_ = o.EncodeError(w, req, err, gatewayerrors.Codes.ToAPIErr(gatewayerrors.ErrNoSuchKey))
		return
	}
	if errors.Is(err, catalog.ErrExpired) {
		_ = o.EncodeError(w, req, err, gatewayerrors.Codes.ToAPIErr(gatewayerrors.ErrNoSuchVersion))
		return
	}
	if err != nil {
		_ = o.EncodeError(w, req, err, gatewayerrors.Codes.ToAPIErr(gatewayerrors.ErrInternalError))
		return
	}

	// TODO: the rest of https://docs.aws.amazon.com/en_pv/AmazonS3/latest/API/API_GetObject.html
	// range query
	var data io.ReadCloser
	var rng httputil.Range
	// range query
	rangeSpec := req.Header.Get("Range")
	if len(rangeSpec) > 0 {
		rng, err = httputil.ParseRange(rangeSpec, entry.Size)
		if err != nil {
			o.Log(req).WithError(err).WithField("range", rangeSpec).Debug("invalid range spec")
			if errors.Is(err, httputil.ErrUnsatisfiableRange) {
				_ = o.EncodeError(w, req, err, gatewayerrors.Codes.ToAPIErr(gatewayerrors.ErrInvalidRange))
				return
			}
		}
		// by here, we have a range we can use.
	}

	statusCode := http.StatusOK
	contentLength := entry.Size
	contentRange := ""
	objectPointer := block.ObjectPointer{
		StorageNamespace: o.Repository.StorageNamespace,
		IdentifierType:   entry.AddressType.ToIdentifierType(),
		Identifier:       entry.PhysicalAddress,
	}

	if rangeSpec == "" || err != nil {
		// assemble a response body (range-less query)
		data, err = o.BlockStore.Get(ctx, objectPointer, entry.Size)
	} else {
		contentLength = rng.Size()
		contentRange = fmt.Sprintf("bytes %d-%d/%d", rng.StartOffset, rng.EndOffset, entry.Size)
		statusCode = http.StatusPartialContent
		data, err = o.BlockStore.GetRange(ctx, objectPointer, rng.StartOffset, rng.EndOffset)
	}
	if err != nil {
		code := gatewayerrors.ErrInternalError
		if errors.Is(err, block.ErrDataNotFound) {
			code = gatewayerrors.ErrNoSuchVersion
		}
		_ = o.EncodeError(w, req, err, gatewayerrors.Codes.ToAPIErr(code))
		return
	}

	o.SetHeader(w, "Last-Modified", httputil.HeaderTimestamp(entry.CreationDate))
	o.SetHeader(w, "ETag", httputil.ETag(entry.Checksum))
	o.SetHeader(w, "Content-Type", entry.ContentType)
	o.SetHeader(w, "Accept-Ranges", "bytes")
	if contentRange != "" {
		o.SetHeader(w, "Content-Range", contentRange)
	}
	o.SetHeader(w, "Content-Length", fmt.Sprintf("%d", contentLength))
	o.SetHeader(w, "X-Content-Type-Options", "nosniff")
	o.SetHeader(w, "X-Frame-Options", "SAMEORIGIN")
	o.SetHeader(w, "Content-Security-Policy", "default-src 'none'")
	amzMetaWriteHeaders(w, entry.Metadata)
	w.WriteHeader(statusCode)

	defer func() {
		_ = data.Close()
	}()
	_, err = io.Copy(w, data)
	if err != nil {
		o.Log(req).WithError(err).Error("could not write response body for object")
	}
}
