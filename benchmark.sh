#!/usr/bin/env bash

FRAMEWORK=$1
TIMESTAMP=$(date +%s)
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
WEB_DIR="$SCRIPT_DIR/$FRAMEWORK/target/jmeter/$TIMESTAMP"

mkdir -p $WEB_DIR && \
  "$SCRIPT_DIR/$FRAMEWORK/target/universal/stage/bin/msdemo-$FRAMEWORK" & MYPID=$! && \
  jmeter -n -t msdemo-test1.jmx -l msdemo-test1-$FRAMEWORK-$TIMESTAMP.csv -e -o "$WEB_DIR" && \
  kill -9 $MYPID


