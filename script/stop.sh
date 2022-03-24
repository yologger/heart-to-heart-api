#!/usr/bin/env bash

echo "Execute stop.sh"
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)
echo "- Idle port: $IDLE_PORT"

IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})
echo "- PID of idle port: $IDLE_PID"

if [ -z ${IDLE_PID} ]
then
  echo "- No running application."
else
  echo "- kill application with pid: $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi