#!/bin/bash

while true ; do
  [ -f /filter/logstash-filter-java_filter_example.gem ] && break && echo "Plugin found.."
  echo "Waiting for plugin.."
  sleep 1
done

logstash-plugin install --no-verify --local /filter/logstash-filter-java_filter_example.gem

logstash --config.reload.automatic