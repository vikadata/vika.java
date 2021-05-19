#!/bin/bash

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

if [ "$TRAVIS_TAG" ]; then
  export GPG_TTY=$(tty)
  mvn clean deploy -Prelease --settings "${TRAVIS_BUILD_DIR}"/.mvn/settings.xml -Dgpg.executable=gpg2 -Dgpg.keyname="$GPG_KEY_NAME"-Dgpg.passphrase="$GPG_PASSPHRASE"
else
  echo "not create a tag"
fi


