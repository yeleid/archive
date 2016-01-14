#!/usr/bin/env bash

IP="172.31.10.199"
PORT="11300"
FILE="sample/test.txt"
ID="123"

curl -i --trace-ascii /dev/stdout -X POST http://$IP:$PORT/archive/nosql/data/put/$ID -F "data=@$FILE"
echo
