#!/usr/bin/env bash

echo "Snapshot Deploy"

mvn --settings .mvn/settings.xml clean deploy  -P sonatype-snapshot