#!/usr/bin/env bash

FRAMEWORK=$1
REMOTE_HOST=$2

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
WEB_DIR="$SCRIPT_DIR/$FRAMEWORK/target/jmeter/"
RESULTS_DIR="$SCRIPT_DIR/results"

IP_ADDRESS=$(curl -s http://checkip.dyndns.org/ | sed 's/[a-zA-Z<>/ :]//g')

"$SCRIPT_DIR/$FRAMEWORK/target/universal/stage/bin/msdemo-$FRAMEWORK" & \
  sleep 10

DATA_DIR=tempdata
ssh $USER@$REMOTE_HOST <<ENDSSH

  mkdir -p $DATA_DIR
  scp $USER@$IP_ADDRESS:$SCRIPT_DIR/msdemo-test1.jmx msdemo-test1.jmx
  sed -i 's/localhost/$IP_ADDRESS/g' msdemo-test1.jmx

  jmeter -n -t msdemo-test1.jmx -l "$DATA_DIR/$FRAMEWORK-jmeter.csv" -e -o "$WEB_DIR" && \
  sleep 2 && \
  wrk -t4 -c400 -d20s "http://$IP_ADDRESS:8080/media/demo/4/4/4" > "$DATA_DIR/$FRAMEWORK-wrk1.txt" && \
  sleep 2 && \
  wrk -t4 -c400 -d20s "http://$IP_ADDRESS:8080/media/demo/4/4/4?readCount=20" > "$DATA_DIR/$FRAMEWORK-wrk2.txt" && \
  sleep 2 && \
  autocannon -c 100 -d 20 -p 10 -j "http://$IP_ADDRESS:8080/media/demo/4/4/4" > "$DATA_DIR/$FRAMEWORK-autocannon1.json" && \
  sleep 2 && \
  autocannon -c 100 -d 20 -p 10 -j "http://$IP_ADDRESS:8080/media/demo/4/4/4?readCount=20" > "$DATA_DIR/$FRAMEWORK-autocannon2.json"
  
  scp -rp $DATA_DIR $USER@$IP_ADDRESS:$RESULTS_DIR
  rm -rf $DATA_DIR
  rm msdemo-test1.jmx
  
ENDSSH

s=$(jps -vl | grep $FRAMEWORK)
id="$( cut -d ' ' -f 1 <<< "$s" )"
kill -9 $id