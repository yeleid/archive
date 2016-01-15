#!/usr/bin/env bash

IP="172.31.10.199"
PORT="11300"
QUERY="a:alex"

curl -i -v -H "Content-Type: application/json" -X POST http://$IP:$PORT/archive/search -d "$QUERY"

echo
