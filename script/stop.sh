#!/usr/bin/env bash

echo " ################# 배포를 시작합니다 ##################"

echo " stop.sh 실행"
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)
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