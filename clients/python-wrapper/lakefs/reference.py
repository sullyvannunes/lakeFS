"""
Module containing lakeFS reference implementation
"""

from __future__ import annotations
from typing import Optional, Generator, NamedTuple, Literal

import lakefs_sdk

from lakefs.client import Client, DefaultClient
from lakefs.exceptions import api_exception_handler
from lakefs.object import Object
from lakefs.object_manager import ObjectManager


class Commit(NamedTuple):
    """
    NamedTuple representing a lakeFS commit's properties
    """
    id: str
    parents: [str]
    committer: str
    message: str
    creation_date: int
    meta_range_id: str
    metadata: Optional[dict[str, str]] = None


class Change(NamedTuple):
    """
    NamedTuple representing a diff change between to refs in lakeFS
    """
    type: Literal["added", "removed", "changed", "conflict", "prefix_changed"]
    path: str
    path_type: Literal["common_prefix", "object"]
    size_bytes: Optional[int]


class Reference:
    """
    Class representing a reference in lakeFS.
    """
    _client: Client
    _repo_id: str
    _id: str
    _commit: Optional[Commit] = None

    def __init__(self, repo_id: str, ref_id: str, client: Optional[Client] = DefaultClient) -> None:
        self._client = client
        self._repo_id = repo_id
        self._id = ref_id

    @property
    def repo_id(self) -> str:
        """
        Return the repository id for this reference
        """
        return self._repo_id

    @property
    def id(self) -> str:
        """
        Returns the reference id
        """
        return self._id

    @property
    def objects(self) -> ObjectManager:
        """
        Returns a ObjectManager object for this reference
        """
        # TODO: Implement

    @staticmethod
    def _get_generator(func, *args, max_amount: Optional[int] = None, **kwargs):
        count = 0
        has_more = True
        with api_exception_handler():
            while has_more:
                page = func(*args, **kwargs)
                has_more = page.pagination.has_more
                for res in page.results:
                    count += 1
                    yield res
                    if max_amount is not None and count >= max_amount:
                        return

    def log(self, max_amount: Optional[int] = None, **kwargs) -> Generator[lakefs_sdk.Commit]:
        """
        Returns a generator of commits starting with this reference id
        :param max_amount: (Optional) limits the amount of results to return from the server
        :param kwargs: Additional keyword arguments
        :raises
            NotFoundException if reference by this id does not exist
            NotAuthorizedException if user is not authorized to perform this operation
            ServerException for any other errors
        """
        if max_amount is not None:
            kwargs["limit"] = True

        return self._get_generator(self._client.sdk_client.refs_api.log_commits,
                                   self._repo_id, self._id, max_amount=max_amount, **kwargs)

    def _get_commit(self):
        if self._commit is None:
            with api_exception_handler():
                commit = self._client.sdk_client.commits_api.get_commit(self._repo_id, self._id)
                self._commit = Commit(**commit.__dict__)
        return self._commit

    def metadata(self) -> dict[str, str]:
        """
        Return commit metadata for this reference id
        """
        return self._get_commit().metadata

    def commit_message(self) -> str:
        """
        Return commit message for this reference id
        """
        return self._get_commit().message

    def commit_id(self) -> str:
        """
        Return commit id for this reference id
        """
        return self._get_commit().id

    def diff(self,
             other_ref: str | Reference,
             max_amount: Optional[int] = None,
             after: str = "",
             prefix: str = "",
             delimiter: str = '/',
             **kwargs) -> Generator[Change]:
        """
        Returns a diff generator of changes between this reference and other_ref
        :param other_ref: The other ref to diff against
        :param max_amount: Stop showing changes after this amount
        :param after: Return items after this value
        :param prefix: Return items prefixed with this value
        :param delimiter: Group common prefixes by this delimiter
        :raises
            NotFoundException if this reference or other_ref does not exist
            NotAuthorizedException if user is not authorized to perform this operation
            ServerException for any other errors
        """
        for diff in self._get_generator(self._client.sdk_client.refs_api.diff_refs,
                                        repository=self._repo_id,
                                        left_ref=self._id,
                                        right_ref=str(other_ref),
                                        after=after,
                                        max_amount=max_amount,
                                        prefix=prefix,
                                        delimiter=delimiter,
                                        **kwargs):
            yield Change(**diff.__dict__)

    def merge_into(self, destination_branch_id: str | Reference, **kwargs) -> str:
        """
        Merge this reference into destination branch
        :param destination_branch_id: The ID of the merge destination
        :return The reference id of the merge commit
        :raises
            NotFoundException if reference by this id does not exist, or branch doesn't exist
            NotAuthorizedException if user is not authorized to perform this operation
            ServerException for any other errors
        """
        with api_exception_handler():
            res = self._client.sdk_client.refs_api.merge_into_branch(self._repo_id,
                                                                     self._id,
                                                                     str(destination_branch_id),
                                                                     **kwargs)
            return res.reference

    def object(self, path: str) -> Object:  # pylint: disable=C0103
        """
        Returns an Object class representing a lakeFS object with this repo id, reference id and path
        :param path: The object's path
        """
        return Object(self._repo_id, self._id, path)

    def __str__(self) -> str:
        return self._id

    def __repr__(self):
        return f"lakefs://{self._repo_id}/{self._id}"
