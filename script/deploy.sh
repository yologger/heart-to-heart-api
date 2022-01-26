#!/bin/bash

REPOSITORY=/home/

java -Dspring.profiles.active=prod -Dserver.port=$PORT -jar heart-to-heart-api-0.0.1.jar