#!/usr/bin/env bash

JAR="target/archive-0.4.0.jar"
CLASS="com.yeleid.solutions.Main"
IP="172.31.13.75"
PORT="11300"

java -cp $JAR $CLASS $IP $PORT

