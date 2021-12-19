#!/usr/bin/env bash
export ARGS=`echo "$@"`
mvn exec:java -Dexec.args="$ARGS"
