#!/usr/bin/env bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
RESULTS_DIR="$SCRIPT_DIR/results"

sbt "clean;stage"

for framework in cask helidon http4s javalin scalatra sparkjava vertx zhttp
do
  benchmark.sh $framework
done

WRK_APP=SCRIPT_DIR/shared/target/universal/stage/bin/shared
$WRK_APP $RESULTS_DIR wrk1 $RESULTS_DIR/all-wrk1-results.csv
$WRK_APP $RESULTS_DIR wrk2 $RESULTS_DIR/all-wrk2-results.csv