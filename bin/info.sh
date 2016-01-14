#!/usr/bin/env bash

IP="172.31.10.199"
PORT="11300"

curl -i -v http://$IP:$PORT/archive/info
echo
