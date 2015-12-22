#!/usr/bin/env bash

JAR="conf:target/archive-0.4.0.jar"
CLASS="com.yeleid.solutions.Main"
IP="127.0.0.1"
# IP="172.31.13.75"
PORT="11300"

java -cp $JAR $CLASS $IP $PORT

