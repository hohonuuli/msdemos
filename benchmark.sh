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
  jmeter -n -t msdemo-test1.jmx -l "$RESULTS_DIR/$FRAMEWORK-jmeter-$TIMESTAMP.csv" -e -o "$WEB_DIR" && \
  sleep 2 && \
  wrk -t4 -c400 -d20s "http://127.0.0.1:8080/media/demo/4/4/4" > "$RESULTS_DIR/$FRAMEWORK-wrk1-$TIMESTAMP.txt" && \
  sleep 2 && \
  wrk -t4 -c400 -d20s "http://127.0.0.1:8080/media/demo/4/4/4?readCount=20" > "$RESULTS_DIR/$FRAMEWORK-wrk2-$TIMESTAMP.txt" && \
  sleep 2 && \
  autocannon -c 100 -d 20 -p 10 -j "http://127.0.0.1:8080/media/demo/4/4/4" > "$RESULTS_DIR/$FRAMEWORK-autocannon1-$TIMESTAMP.json" && \
  sleep 2 && \
  autocannon -c 100 -d 20 -p 10 -j "http://127.0.0.1:8080/media/demo/4/4/4?readCount=20" > "$RESULTS_DIR/$FRAMEWORK-autocannon2-$TIMESTAMP.json"

s=$(jps -vl | grep $FRAMEWORK)
id="$( cut -d ' ' -f 1 <<< "$s" )"
kill -9 $id
