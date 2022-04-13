#!/bin/bash

echo -e "[Copying Pipelines]"
rm -rf /pipelines/output/*
cp -Rv pipelines/input/* /pipelines/output

echo -e "\n[Copied Pipelines]"
find /pipelines/output -mindepth 1