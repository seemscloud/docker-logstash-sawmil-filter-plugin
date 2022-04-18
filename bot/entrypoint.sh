#!/bin/bash

while true; do
  curl -X PUT http://logstash:8080 \
    -H "Content-Type: application/json" \
    -d @events/foo.json --silent > /dev/null

  curl -X PUT http://logstash:8080 \
    -H "Content-Type: application/json" \
    -d @events/bar.json --silent > /dev/null

  curl -X PUT http://logstash:8080 \
    -H "Content-Type: application/json" \
    -d @events/moo.json --silent > /dev/null

  sleep 1
done
