#!/bin/bash

for i in `seq 1 "${LINES}"` ; do echo "Lorem Ipsum is simply dummy text of the printing and typesetting industry." >> server.log ; done

filebeat -e -strict.perms=false