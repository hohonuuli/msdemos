#!/usr/bin/env bash

sbt "clean;stage"

for framework in cask helidon http4s javalin scalatra sparkjava vertx zhttp
do
  benchmark.sh $framework
done