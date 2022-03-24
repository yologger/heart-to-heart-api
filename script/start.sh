#!/usr/bin/env bash

echo "> execute 'stop.sh'"

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/heart-to-heart-api
PROJECT_NAME=heart-to-heart-api

cp $REPOSITORY/zip/build/libs/*.jar $REPOSITORY

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

chmod +x $REPOSITORY/$JAR_NAME

IDLE_PROFILE=$(find_idle_profile)

nohup java
    -Dspring.config.location=/home/ec2-user/app/heart-to-heart-api/application-$IDLE_PROFILE.properties
    -Dspring.profiles.active=dev -jar $REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &