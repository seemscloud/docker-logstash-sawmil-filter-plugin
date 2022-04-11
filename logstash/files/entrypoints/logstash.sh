#!/bin/bash

while true ; do
  [ -f /filter/logstash-filter-sawmill-1.0.0.gem ] && break && echo "Plugin found.."
  echo "Waiting for plugin.."
  sleep 1
done

logstash-plugin install --no-verify --local /filter/logstash-filter-sawmill-1.0.0.gem

logstash --config.reload.automatic