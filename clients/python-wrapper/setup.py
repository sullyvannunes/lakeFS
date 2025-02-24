from setuptools import setup, find_packages

NAME = "lakefs"
VERSION = "0.1.0-alpha.1"
# To install the library, run the following
#
# python setup.py install
#
# prerequisite: setuptools
# http://pypi.python.org/pypi/setuptools

PYTHON_REQUIRES = ">=3.9"
REQUIRES = [
    "pydantic >= 1.10.5, < 2",
    "setuptools == 68.2.2",
    "lakefs-sdk ~= 1.1.0",
    "pyyaml ~= 6.0.1",
]

# TODO: autogenerate docs and grab long description from docs
long_description = (
    "A Wrapper package for the lakeFS Python SDK providing a simpler programming interface with less configuration and "
    "better abstractions for common, more complex operations")

setup(
    name=NAME,
    version=VERSION,
    description="lakeFS Python SDK Wrapper",
    author="Treeverse",
    author_email="services@treeverse.io",
    url="https://github.com/treeverse/lakeFS/tree/master/clients/python-wrapper",
    keywords=["OpenAPI", "OpenAPI-Generator", "lakeFS API", "Python Wrapper"],
    python_requires=">=3.9",
    install_requires=REQUIRES,
    tests_require=["pytest ~= 7.4.3"],
    packages=find_packages(exclude=["tests"]),
    include_package_data=True,
    license="Apache 2.0",
    long_description=long_description,
    long_description_content_type='text/markdown'
)
