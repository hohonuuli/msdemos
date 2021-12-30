#!/usr/bin/env bash

REMOTE_HOST=$1

sbt "clean;stage"

for framework in cask helidon http4s javalin scalatra sparkjava vertx zhttp
do
  benchmark-remote.sh $framework $REMOTE_HOST
done