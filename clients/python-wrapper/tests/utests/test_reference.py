import lakefs_sdk

from lakefs.repository import Repository

from tests.utests.common import get_test_client


def get_test_ref():
    client = get_test_client()
    repo = Repository(repository_id="test_repo", client=client)
    return repo.ref("test_reference")


def test_reference_creation():
    ref = get_test_ref()
    assert ref._repo_id == "test_repo"
    assert ref.id == "test_reference"


def test_reference_log(monkeypatch):
    ref = get_test_ref()
    idx = 0
    pages = 10
    items_per_page = 100

    def monkey_log_commits(*_, **__):
        nonlocal idx
        results = []
        for pid in range(items_per_page):
            index = items_per_page * idx + pid
            results.append(lakefs_sdk.Commit(
                id=str(index),
                parents=[""],
                committer="Commiter-" + str(index),
                message="Message-" + str(index),
                creation_date=index,
                meta_range_id="",
            ))
        idx += 1
        pagination = lakefs_sdk.Pagination(
            has_more=idx < pages,
            next_offset="",
            max_per_page=items_per_page,
            results=items_per_page
        )

        return lakefs_sdk.CommitList(
            pagination=pagination,
            results=results
        )

    with monkeypatch.context():
        monkeypatch.setattr(ref._client.sdk_client.refs_api, "log_commits", monkey_log_commits)
        i = 0
        # Test log entire history
        for i, c in enumerate(ref.log()):
            assert i == int(c.id)

        assert i + 1 == pages * items_per_page

        # Test log with limit
        idx = 0
        max_amount = 123
        assert len(list(ref.log(max_amount=max_amount))) == max_amount

        # Test limit more than amount
        idx = 0
        max_amount = pages * items_per_page * 2
        assert len(list(ref.log(max_amount=max_amount))) == pages * items_per_page


def test_reference_diff(monkeypatch):
    ref = get_test_ref()
    idx = 0
    pages = 10
    items_per_page = 100

    def monkey_diff_refs(*_, **__):
        nonlocal idx
        results = []
        for pid in range(items_per_page):
            index = items_per_page * idx + pid
            results.append(lakefs_sdk.Diff(
                type="added",
                path=str(index),
                path_type="object",
                size_bytes=index,
            ))
        idx += 1
        pagination = lakefs_sdk.Pagination(
            has_more=idx < pages,
            next_offset="",
            max_per_page=items_per_page,
            results=items_per_page
        )

        return lakefs_sdk.DiffList(
            pagination=pagination,
            results=results
        )

    with monkeypatch.context():
        monkeypatch.setattr(ref._client.sdk_client.refs_api, "diff_refs", monkey_diff_refs)
        # Test log entire history
        i = 0
        for i, c in enumerate(ref.diff(other_ref="other_ref")):
            assert i == c.size_bytes
            assert i == int(c.path)

        assert i + 1 == pages * items_per_page

        # Test log with limit
        idx = 0
        max_amount = 123
        assert len(list(ref.diff(other_ref="other_ref", max_amount=max_amount))) == max_amount

        # Test limit more than amount
        idx = 0
        max_amount = pages * items_per_page * 2
        assert len(list(ref.diff(other_ref="other_ref", max_amount=max_amount))) == pages * items_per_page
