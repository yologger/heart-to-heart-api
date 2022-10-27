#!/bin/bash

REPOSITORY=/home/ec2-user/app/heart-to-heart-api
PROJECT_NAME=heart-to-heart-api

# Jar 복사
cp $REPOSITORY/zip/build/libs/*.jar $REPOSITORY

# 현재 구동 중인 앱의 PID 확인
CURRENT_PID=$(pgrep -fl heart-to-heart-api.*.jar | awk '{print $1}')
if [ -z $CURRENT_PID ]
then
  # 현재 구동 중인 앱이 없을 때
  echo "> No running application."
else
  # 현재 구동 중인 앱이 있 때
  echo "> Application is running."
  echo "> kill pid $CURRENT_PID."
  # 구동 중인 앱 종료
  sudo kill -15 "$CURRENT_PID"
  sleep 5
fi

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

chmod +x $REPOSITORY/$JAR_NAME

nohup java -Dspring.config.location=/home/ec2-user/app/heart-to-heart-api/application-dev.properties -Dspring.profiles.active=dev -jar $REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &

echo "> Application launched."