#!/usr/bin/env bash
export ARGS=`echo "$@"`
mvn package
java -cp .\target\Catane-0.0.1-SNAPSHOT.jar com.catane.view.CLILauncher "$ARGS"
