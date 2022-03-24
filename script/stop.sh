#!/usr/bin/env bash

echo "> execute 'stop.sh'"
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)
echo "> IDLE PORT: $IDLE_PORT"

IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})
echo "> IDLE PORT로 pid 확인: $IDLE_PID"

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi