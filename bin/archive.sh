#!/usr/bin/env bash

JAR="conf:/etc/hbase/conf:target/archive-0.4.0.jar"
CLASS="com.yeleid.solutions.Main"
IP="172.31.10.199"
PORT="11300"

java -cp $JAR $CLASS $IP $PORT

