#!/usr/bin/env bash

FRAMEWORK=$1
REMOTE_HOST=$2

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
WEB_DIR="$SCRIPT_DIR/$FRAMEWORK/target/jmeter/"
RESULTS_DIR="$SCRIPT_DIR/results"
mkdir -p results

# IP_ADDRESS=$(curl -s http://checkip.dyndns.org/ | sed 's/[a-zA-Z<>/ :]//g')
IP_ADDRESS=192.168.86.218

"$SCRIPT_DIR/$FRAMEWORK/target/universal/stage/bin/msdemo-$FRAMEWORK" & \
  sleep 8

DATA_DIR=tempdata
DATA_RESULTS_DIR=$DATA_DIR/results
ssh $USER@$REMOTE_HOST <<ENDSSH

  mkdir -p $DATA_DIR
  scp $USER@$IP_ADDRESS:$SCRIPT_DIR/msdemo-test1.jmx "$DATA_DIR/msdemo-test1.jmx"
  sed -i '' 's/localhost/$IP_ADDRESS/g' "$DATA_DIR/msdemo-test1.jmx"

  jmeter -n -t "$DATA_DIR/msdemo-test1.jmx" -l "$DATA_RESULTS_DIR/$FRAMEWORK-jmeter.csv" -e -o "$DATA_RESULTS_DIR" && \
  sleep 2 && \
  wrk -t4 -c400 -d20s "http://$IP_ADDRESS:8080/media/demo/4/4/4" > "$DATA_RESULTS_DIR/$FRAMEWORK-wrk1.txt" && \
  sleep 2 && \
  wrk -t4 -c400 -d20s "http://$IP_ADDRESS:8080/media/demo/4/4/4?readCount=20" > "$DATA_RESULTS_DIR/$FRAMEWORK-wrk2.txt" && \
  sleep 2 && \
  # autocannon -c 100 -d 20 -p 10 -j "http://$IP_ADDRESS:8080/media/demo/4/4/4" > "$DATA_RESULTS_DIR/$FRAMEWORK-autocannon1.json" && \
  # sleep 2 && \
  # autocannon -c 100 -d 20 -p 10 -j "http://$IP_ADDRESS:8080/media/demo/4/4/4?readCount=20" > "$DATA_RESULTS_DIR/$FRAMEWORK-autocannon2.json"
  
  scp -rp $DATA_RESULTS_DIR $USER@$IP_ADDRESS:$RESULTS_DIR
  rm -rf $DATA_DIR
  
ENDSSH

s=$(jps -vl | grep $FRAMEWORK)
id="$( cut -d ' ' -f 1 <<< "$s" )"
kill -9 $id