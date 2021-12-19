#!/usr/bin/env bash
export ARGS=`echo "$@"`
mvn package
mvn exec:java -Dexec.args="$ARGS"
