#!/usr/bin/env bash

FRAMEWORK=$1
TIMESTAMP=$(date +%s)
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
WEB_DIR="$SCRIPT_DIR/$FRAMEWORK/target/jmeter/$TIMESTAMP"
RESULTS_DIR="$SCRIPT_DIR/results"

mkdir -p $WEB_DIR && \
  mkdir -p $RESULTS_DIR && \
  "$SCRIPT_DIR/$FRAMEWORK/target/universal/stage/bin/msdemo-$FRAMEWORK" & \
  sleep 10 && \
  jmeter -n -t msdemo-test1.jmx -l "$RESULTS_DIR/jmeter/msdemo-test1-$FRAMEWORK-$TIMESTAMP.csv" -e -o "$WEB_DIR" 


