#!/bin/bash

while true; do
  MESSAGE="${LOG_LINE}"

  curl -X PUT http://logstash:8080 \
    -H "Content-Type: application/json" \
    -d @event.json --silent > /dev/null

#  sleep 1
done
