#!/usr/bin/env bash

IP="127.0.0.1"
# IP="172.31.13.75"
PORT="11300"
FILE="sample/test.png"
ID="123"

curl -i --trace-ascii /dev/stdout -X POST http://$IP:$PORT/archive/nosql/data/put/$ID -F "data=@$FILE"
echo
