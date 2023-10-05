/*
 * lakeFS API
 * lakeFS HTTP API
 *
 * The version of the OpenAPI document: 0.1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.lakefs.clients.api;

import io.lakefs.clients.api.ApiCallback;
import io.lakefs.clients.api.ApiClient;
import io.lakefs.clients.api.ApiException;
import io.lakefs.clients.api.ApiResponse;
import io.lakefs.clients.api.Configuration;
import io.lakefs.clients.api.Pair;
import io.lakefs.clients.api.ProgressRequestBody;
import io.lakefs.clients.api.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import io.lakefs.clients.api.model.Commit;
import io.lakefs.clients.api.model.CommitCreation;
import io.lakefs.clients.api.model.Error;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommitsApi {
    private ApiClient localVarApiClient;

    public CommitsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public CommitsApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for commit
     * @param repository  (required)
     * @param branch  (required)
     * @param commitCreation  (required)
     * @param sourceMetarange The source metarange to commit. Branch must not have uncommitted changes. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> commit </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation Error </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Resource Not Found </td><td>  -  </td></tr>
        <tr><td> 412 </td><td> Precondition Failed (e.g. a pre-commit hook returned a failure) </td><td>  -  </td></tr>
        <tr><td> 420 </td><td> too many requests </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> Internal Server Error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call commitCall(String repository, String branch, CommitCreation commitCreation, String sourceMetarange, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = commitCreation;

        // create path and map variables
        String localVarPath = "/repositories/{repository}/branches/{branch}/commits"
            .replaceAll("\\{" + "repository" + "\\}", localVarApiClient.escapeString(repository.toString()))
            .replaceAll("\\{" + "branch" + "\\}", localVarApiClient.escapeString(branch.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (sourceMetarange != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("source_metarange", sourceMetarange));
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "basic_auth", "cookie_auth", "jwt_token", "oidc_auth", "saml_auth" };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call commitValidateBeforeCall(String repository, String branch, CommitCreation commitCreation, String sourceMetarange, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'repository' is set
        if (repository == null) {
            throw new ApiException("Missing the required parameter 'repository' when calling commit(Async)");
        }
        
        // verify the required parameter 'branch' is set
        if (branch == null) {
            throw new ApiException("Missing the required parameter 'branch' when calling commit(Async)");
        }
        
        // verify the required parameter 'commitCreation' is set
        if (commitCreation == null) {
            throw new ApiException("Missing the required parameter 'commitCreation' when calling commit(Async)");
        }
        

        okhttp3.Call localVarCall = commitCall(repository, branch, commitCreation, sourceMetarange, _callback);
        return localVarCall;

    }

    /**
     * create commit
     * 
     * @param repository  (required)
     * @param branch  (required)
     * @param commitCreation  (required)
     * @param sourceMetarange The source metarange to commit. Branch must not have uncommitted changes. (optional)
     * @return Commit
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> commit </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation Error </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Resource Not Found </td><td>  -  </td></tr>
        <tr><td> 412 </td><td> Precondition Failed (e.g. a pre-commit hook returned a failure) </td><td>  -  </td></tr>
        <tr><td> 420 </td><td> too many requests </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> Internal Server Error </td><td>  -  </td></tr>
     </table>
     */
    public Commit commit(String repository, String branch, CommitCreation commitCreation, String sourceMetarange) throws ApiException {
        ApiResponse<Commit> localVarResp = commitWithHttpInfo(repository, branch, commitCreation, sourceMetarange);
        return localVarResp.getData();
    }

    /**
     * create commit
     * 
     * @param repository  (required)
     * @param branch  (required)
     * @param commitCreation  (required)
     * @param sourceMetarange The source metarange to commit. Branch must not have uncommitted changes. (optional)
     * @return ApiResponse&lt;Commit&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> commit </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation Error </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Resource Not Found </td><td>  -  </td></tr>
        <tr><td> 412 </td><td> Precondition Failed (e.g. a pre-commit hook returned a failure) </td><td>  -  </td></tr>
        <tr><td> 420 </td><td> too many requests </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> Internal Server Error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Commit> commitWithHttpInfo(String repository, String branch, CommitCreation commitCreation, String sourceMetarange) throws ApiException {
        okhttp3.Call localVarCall = commitValidateBeforeCall(repository, branch, commitCreation, sourceMetarange, null);
        Type localVarReturnType = new TypeToken<Commit>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * create commit (asynchronously)
     * 
     * @param repository  (required)
     * @param branch  (required)
     * @param commitCreation  (required)
     * @param sourceMetarange The source metarange to commit. Branch must not have uncommitted changes. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> commit </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Validation Error </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Forbidden </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Resource Not Found </td><td>  -  </td></tr>
        <tr><td> 412 </td><td> Precondition Failed (e.g. a pre-commit hook returned a failure) </td><td>  -  </td></tr>
        <tr><td> 420 </td><td> too many requests </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> Internal Server Error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call commitAsync(String repository, String branch, CommitCreation commitCreation, String sourceMetarange, final ApiCallback<Commit> _callback) throws ApiException {

        okhttp3.Call localVarCall = commitValidateBeforeCall(repository, branch, commitCreation, sourceMetarange, _callback);
        Type localVarReturnType = new TypeToken<Commit>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getCommit
     * @param repository  (required)
     * @param commitId  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> commit </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Resource Not Found </td><td>  -  </td></tr>
        <tr><td> 420 </td><td> too many requests </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> Internal Server Error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getCommitCall(String repository, String commitId, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/repositories/{repository}/commits/{commitId}"
            .replaceAll("\\{" + "repository" + "\\}", localVarApiClient.escapeString(repository.toString()))
            .replaceAll("\\{" + "commitId" + "\\}", localVarApiClient.escapeString(commitId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "basic_auth", "cookie_auth", "jwt_token", "oidc_auth", "saml_auth" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getCommitValidateBeforeCall(String repository, String commitId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'repository' is set
        if (repository == null) {
            throw new ApiException("Missing the required parameter 'repository' when calling getCommit(Async)");
        }
        
        // verify the required parameter 'commitId' is set
        if (commitId == null) {
            throw new ApiException("Missing the required parameter 'commitId' when calling getCommit(Async)");
        }
        

        okhttp3.Call localVarCall = getCommitCall(repository, commitId, _callback);
        return localVarCall;

    }

    /**
     * get commit
     * 
     * @param repository  (required)
     * @param commitId  (required)
     * @return Commit
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> commit </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Resource Not Found </td><td>  -  </td></tr>
        <tr><td> 420 </td><td> too many requests </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> Internal Server Error </td><td>  -  </td></tr>
     </table>
     */
    public Commit getCommit(String repository, String commitId) throws ApiException {
        ApiResponse<Commit> localVarResp = getCommitWithHttpInfo(repository, commitId);
        return localVarResp.getData();
    }

    /**
     * get commit
     * 
     * @param repository  (required)
     * @param commitId  (required)
     * @return ApiResponse&lt;Commit&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> commit </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Resource Not Found </td><td>  -  </td></tr>
        <tr><td> 420 </td><td> too many requests </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> Internal Server Error </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Commit> getCommitWithHttpInfo(String repository, String commitId) throws ApiException {
        okhttp3.Call localVarCall = getCommitValidateBeforeCall(repository, commitId, null);
        Type localVarReturnType = new TypeToken<Commit>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * get commit (asynchronously)
     * 
     * @param repository  (required)
     * @param commitId  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> commit </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Unauthorized </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Resource Not Found </td><td>  -  </td></tr>
        <tr><td> 420 </td><td> too many requests </td><td>  -  </td></tr>
        <tr><td> 0 </td><td> Internal Server Error </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getCommitAsync(String repository, String commitId, final ApiCallback<Commit> _callback) throws ApiException {

        okhttp3.Call localVarCall = getCommitValidateBeforeCall(repository, commitId, _callback);
        Type localVarReturnType = new TypeToken<Commit>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
