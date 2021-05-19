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

version=$(mvn exec:exec -q -N -Dexec.executable="echo" -Dexec.args='${project.version}' | awk -F '-' '{print $2}')
echo "${version}"

if [ "$version" ]; then
  export GPG_TTY=$(tty)
  mvn clean deploy -Psnapshot --settings "${TRAVIS_BUILD_DIR}"/.mvn/settings.xml -Dgpg.executable=gpg2 -Dgpg.keyname="$GPG_KEY_NAME"-Dgpg.passphrase="$GPG_PASSPHRASE"
fi
