#!/bin/bash

./gradlew clean
./gradlew bootJar

if [[ $1 == "docker" ]]
then
  docker build -t echo-bot .
  dokcer run -dp 8080:8080 echo-bot
elif [[ $1 == "" ]]
then
  java -jar build/libs/echo-bot-1.0.jar
else
  echo "Unknown arg $1"
  exit -1
fi
