#!/usr/bin/env bash

IP="127.0.0.1"
# IP="172.31.13.75"
PORT="11300"
ID="123"

curl -i -v http://$IP:$PORT/archive/nosql/meta/get/$ID

echo
