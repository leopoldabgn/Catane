#!/usr/bin/env bash
export ARGS=`echo "$@"`
mvn package
clear
mvn exec:java -Dexec.args="$ARGS"
