#!/usr/bin/env bash

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

if [ "${IDLE_PROFILE}" == dev1 ] || [ "${IDLE_PROFILE}" == prod1 ]; then
  IDLE_PORT="8081"
else
  IDLE_PORT="8082"
fi

echo " - IDLE_PORT: $IDLE_PORT"
echo " - curl -s http://localhost:$IDLE_PORT/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)

  if [ "$DEPLOYMENT_GROUP_NAME" == "h2h-code-deploy-group-dev" ]
  then
    UP_COUNT=$(echo ${RESPONSE} | grep 'dev' | wc -l)
  fi

  if [ "$DEPLOYMENT_GROUP_NAME" == "h2h-code-deploy-group-prod" ]
  then
    UP_COUNT=$(echo ${RESPONSE} | grep 'prod' | wc -l)
  fi

  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1 ("real" 문자열이 있는지 검증)
      echo " - Health check 성공"
      echo " - Port를 전환합니다."
      echo " - 전환할 Port: $IDLE_PORT"
      echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

      echo " - Reload nginx"
      sudo service nginx reload
      break
  else
      echo " - Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다."
      echo " - Health check: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo " - Health check 실패. "
    echo " - 엔진엑스에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo " - Health check 연결 실패. 재시도..."
  sleep 10
done