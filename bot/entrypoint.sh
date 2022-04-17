#!/bin/bash

while true; do
  MESSAGE="${LOG_LINE}"

  curl -X PUT http://logstash:8080 \
    -H "Content-Type: application/json" \
    -d @event.json --silent > /dev/null

  if [ "${?}" == "0" ]; then
    echo "Sent event to Logstash.."
  else
    echo "Failed send event.."
  fi

  sleep 1
done
