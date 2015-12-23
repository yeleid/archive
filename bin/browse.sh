#!/usr/bin/env bash

IP="127.0.0.1"
# IP="172.31.13.75"
PORT="11300"
ID="809d0934-17a1-4e26-a633-034fb11459e6"
FILENAME="test.txt"
AUTHOR="alex"

curl -i --trace-ascii /dev/stdout -H "Content-Type: application/json" -X POST http://$IP:$PORT/archive/nosql/meta/put -d "{\"filename\":\"$FILENAME\",\"author\":\"$AUTHOR\"}"

echo
