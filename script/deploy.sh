#!/bin/bash

REPOSITORY=/home/ec2-user/app/heart-to-heart-api
PROJECT_NAME=heart-to-heart-api

echo -e "[$(date "+%Y-%m-%d %I:%M:%S")] Copy jar to project root." > $REPOSITORY/deploy_log.txt
cp $REPOSITORY/zip/build/libs/*.jar $REPOSITORY

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
if [ -z $CURRENT_PID ]
then
  echo "[$(date "+%Y-%m-%d %I:%M:%S")] There is no previously running application." >> $REPOSITORY/deploy_log.txt
else
  echo "[$(date "+%Y-%m-%d %I:%M:%S")] A previously running application exists." >> $REPOSITORY/deploy_log.txt
  echo "[$(date "+%Y-%m-%d %I:%M:%S")] kill -15 $CURRENT_PID" >> $REPOSITORY/deploy_log.txt
  sudo kill -15 "$CURRENT_PID"
  sleep 5
fi

echo "[$(date "+%Y-%m-%d %I:%M:%S")] Launch new application." >> $REPOSITORY/deploy_log.txt
JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
echo "[$(date "+%Y-%m-%d %I:%M:%S")] JAR_NAME: ${JAR_NAME}" >> $REPOSITORY/deploy_log.txt
chmod +x $JAR_NAME
nohup java -Dspring.profiles.active=prod -jar $REPOSITORY/"$JAR_NAME" > /dev/null 2> /dev/null < /dev/null &
#nohup java -Dspring.profiles.active=prod -jar $REPOSITORY/"$JAR_NAME" 2>&1 &
# nohup java -Dspring.profiles.active=prod -jar /home/ec2-user/app/heart-to-heart-api/heart-to-heart-api-0.0.1.jar 2>&1 &