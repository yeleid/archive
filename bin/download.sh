#!/usr/bin/env bash

IP="172.31.10.199"
PORT="11300"
ID="123"
DIR="/tmp/staging"

rm -rf $DIR
mkdir -p $DIR
cd $DIR && curl -v -O http://$IP:$PORT/archive/nosql/data/get/$ID
echo

