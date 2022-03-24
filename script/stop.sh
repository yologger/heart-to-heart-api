#!/usr/bin/env bash

echo " ################# 배포를 시작합니다 ##################"

## 개발 환경인 경우
if [ "$DEPLOYMENT_GROUP_NAME" == "h2h-code-deploy-group-dev" ]
then
    echo " - 개발 환경에 배포를 시도합니다."
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
    echo " - 운영 환경에 배포를 시도합니다."
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

IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})
echo " - PID of idle port: $IDLE_PID"

if [ -z ${IDLE_PID} ]
then
  echo " - No running application."
else
  echo " - Application is already running."
  echo " - kill application with pid: $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi