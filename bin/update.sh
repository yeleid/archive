#!/usr/bin/env bash

IP="172.31.10.199"
PORT="11300"
ID="123"
FILENAME="test.txt"
AUTHOR="alex"

curl -i --trace-ascii /dev/stdout -H "Content-Type: application/json" -X POST http://$IP:$PORT/archive/nosql/meta/put/$ID -d "{\"filename\":\"$FILENAME\",\"author\":\"$AUTHOR\"}"

echo
