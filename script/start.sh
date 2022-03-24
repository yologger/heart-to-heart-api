#!/usr/bin/env bash

echo " #### start.sh 실행"

## 개발 환경인 경우
if [ "$DEPLOYMENT_GROUP_NAME" == "h2h-code-deploy-group-dev" ]
then
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 ~ 500 에러
    then
        CURRENT_PROFILE=dev2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == dev1 ]
    then
      IDLE_PROFILE=dev2
    else
      IDLE_PROFILE=dev1
    fi
fi

## 운영 환경인 경우
if [ "$DEPLOYMENT_GROUP_NAME" == "h2h-code-deploy-group-prod" ]
then
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] # 400 ~ 500 에러
    then
        CURRENT_PROFILE=prod2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == prod1 ]
    then
      IDLE_PROFILE=prod2
    else
      IDLE_PROFILE=prod1
    fi
fi

echo " - IDLE PROFILE: ${IDLE_PROFILE}"

if [ "${IDLE_PROFILE}" == dev1 ] || [ "${IDLE_PROFILE}" == prod1 ]; then
  IDLE_PORT="8081"
else
  IDLE_PORT="8082"
fi

echo " - IDLE PORT: $IDLE_PORT"

REPOSITORY=/home/ec2-user/app/heart-to-heart-api
PROJECT_NAME=heart-to-heart-api

cp $REPOSITORY/zip/build/libs/*.jar $REPOSITORY

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo " - JAR_NAME: ${JAR_NAME}"

echo " - JAR 권한 부여"
chmod +x $REPOSITORY/$JAR_NAME

nohup java -Dspring.config.location=/home/ec2-user/app/heart-to-heart-api/application-$IDLE_PROFILE.properties -Dspring.profiles.active=$IDLE_PROFILE -jar $REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
echo " - 앱 실행 완료"