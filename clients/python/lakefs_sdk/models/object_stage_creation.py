# coding: utf-8

"""
    lakeFS API

    lakeFS HTTP API

    The version of the OpenAPI document: 1.0.0
    Contact: services@treeverse.io
    Generated by OpenAPI Generator (https://openapi-generator.tech)

    Do not edit the class manually.
"""  # noqa: E501


from __future__ import annotations
import pprint
import re  # noqa: F401
import json


from typing import Dict, Optional
from pydantic import BaseModel, Field, StrictInt, StrictStr

class ObjectStageCreation(BaseModel):
    """
    ObjectStageCreation
    """
    physical_address: StrictStr = Field(...)
    checksum: StrictStr = Field(...)
    size_bytes: StrictInt = Field(...)
    mtime: Optional[StrictInt] = Field(None, description="Unix Epoch in seconds")
    metadata: Optional[Dict[str, StrictStr]] = None
    content_type: Optional[StrictStr] = Field(None, description="Object media type")
    __properties = ["physical_address", "checksum", "size_bytes", "mtime", "metadata", "content_type"]

    class Config:
        """Pydantic configuration"""
        allow_population_by_field_name = True
        validate_assignment = True

    def to_str(self) -> str:
        """Returns the string representation of the model using alias"""
        return pprint.pformat(self.dict(by_alias=True))

    def to_json(self) -> str:
        """Returns the JSON representation of the model using alias"""
        return json.dumps(self.to_dict())

    @classmethod
    def from_json(cls, json_str: str) -> ObjectStageCreation:
        """Create an instance of ObjectStageCreation from a JSON string"""
        return cls.from_dict(json.loads(json_str))

    def to_dict(self):
        """Returns the dictionary representation of the model using alias"""
        _dict = self.dict(by_alias=True,
                          exclude={
                          },
                          exclude_none=True)
        return _dict

    @classmethod
    def from_dict(cls, obj: dict) -> ObjectStageCreation:
        """Create an instance of ObjectStageCreation from a dict"""
        if obj is None:
            return None

        if not isinstance(obj, dict):
            return ObjectStageCreation.parse_obj(obj)

        _obj = ObjectStageCreation.parse_obj({
            "physical_address": obj.get("physical_address"),
            "checksum": obj.get("checksum"),
            "size_bytes": obj.get("size_bytes"),
            "mtime": obj.get("mtime"),
            "metadata": obj.get("metadata"),
            "content_type": obj.get("content_type")
        })
        return _obj


