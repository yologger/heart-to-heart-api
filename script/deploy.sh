#!/bin/bash

REPOSITORY=/home/ec2-user/app/heart-to-heart-api
PROJECT_NAME=heart-to-heart-api

echo -e "[$(date "+%Y-%m-%d %I:%M:%S")] Copy jar to project root."
cp $REPOSITORY/zip/build/libs/*.jar $REPOSITORY

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
if [ -z $CURRENT_PID ]
then
  echo "[$(date "+%Y-%m-%d %I:%M:%S")] There is no previously running application."
else
  echo "[$(date "+%Y-%m-%d %I:%M:%S")] A previously running application exists."
  echo "[$(date "+%Y-%m-%d %I:%M:%S")] kill -15 $CURRENT_PID" >> $REPOSITORY/deploy_log.txt
  sudo kill -15 "$CURRENT_PID"
  sleep 5
fi

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)
echo "[$(date "+%Y-%m-%d %I:%M:%S")] JAR_NAME: ${JAR_NAME}"

echo "[$(date "+%Y-%m-%d %I:%M:%S")] Add execution permission to ${JAR_NAME}"
chmod +x $REPOSITORY/$JAR_NAME

echo "[$(date "+%Y-%m-%d %I:%M:%S")] Launch new application."

# nohup java -Dspring.profiles.active=prod -jar $REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
nohup java -Dspring.config.location=classpath:/application-prod.properties -Dspring.profiles.active=prod -jar $REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

echo "[$(date "+%Y-%m-%d %I:%M:%S")] Application started."