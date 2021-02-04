#!/usr/bin/env bash


set -e

# Check the variables are set
if [ -z "$SONATYPE_USERNAME" ]; then
  echo "missing environment value: SONATYPE_USERNAME" >&2
  exit 1
fi

if [ -z "$SONATYPE_PASSWORD" ]; then
  echo "missing environment value: SONATYPE_PASSWORD" >&2
  exit 1
fi

if [ -z "$GPG_KEY_NAME" ]; then
  echo "missing environment value: GPG_KEY_NAME" >&2
  exit 1
fi

if [ -z "$GPG_PASSPHRASE" ]; then
  echo "missing environment value: GPG_PASSPHRASE" >&2
  exit 1
fi

mvn clean deploy -Psnapshot -DskipTests=true --settings "${TRAVIS_BUILD_DIR}/.mvn/settings.xml"