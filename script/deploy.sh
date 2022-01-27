#!/bin/bash

echo "RESULT" > /home/ec2-user/app/heart-to-heart-api/test.txt

#REPOSITORY=/home/ec2-user/app/heart-to-heart-api
#PROJECT_NAME=heart-to-heart-api
#
#echo ">>> Copy jar to project root."
#cp $REPOSITORY/zip/build/libs/*.jar $REPOSITORY
#
#CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
#
#if [ -z "$CURRENT_PID" ]; then
#  echo ">>> There is no previously running application."
#else
#  echo ">>> A previously running application exists."
#  echo ">>> kill -15 $$CURRENT_PID"
#  kill -15 $$CURRENT_PID
#  sleep 5
#fi
#
#echo ">>> Launch new application."
#
#JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
#echo ">>> JAR_NAME: ${JAR_NAME}"
#
#nohup java -Dspring.profiles.active=prod -jar $REPOSITORY/"$JAR_NAME" 2>&1

# java -jar heart-to-heart-api-0.0.1.jar
# nohup java -Dspring.profiles.active=prod -jar heart-to-heart-api-0.0.1.jar 2>&1 &