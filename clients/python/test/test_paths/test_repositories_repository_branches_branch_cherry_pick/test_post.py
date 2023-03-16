# coding: utf-8

"""


    Generated by: https://openapi-generator.tech
"""

import unittest
from unittest.mock import patch

import urllib3

import lakefs_client
from lakefs_client.paths.repositories_repository_branches_branch_cherry_pick import post  # noqa: E501
from lakefs_client import configuration, schemas, api_client

from .. import ApiTestMixin


class TestRepositoriesRepositoryBranchesBranchCherryPick(ApiTestMixin, unittest.TestCase):
    """
    RepositoriesRepositoryBranchesBranchCherryPick unit test stubs
        Cherry-Pick the given reference commit into the given branch  # noqa: E501
    """
    _configuration = configuration.Configuration()

    def setUp(self):
        used_api_client = api_client.ApiClient(configuration=self._configuration)
        self.api = post.ApiForpost(api_client=used_api_client)  # noqa: E501

    def tearDown(self):
        pass

    response_status = 201






if __name__ == '__main__':
    unittest.main()
