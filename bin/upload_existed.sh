#!/usr/bin/env bash

IP="127.0.0.1"
# IP="172.31.13.75"
PORT="11300"
FILE="sample/test.png"

curl -i --trace-ascii /dev/stdout -X POST http://$IP:$PORT/archive/nosql/data/put -F "id=809d0934-17a1-4e26-a633-034fb11459e6" -F "data=@$FILE"
echo
