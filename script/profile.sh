# 쉬고있는 Profile 찾기
function find_idle_profile()
{
    ## 개발 환경일 경우
    if [ "$DEPLOYMENT_GROUP_NAME" == "h2h-code-deploy-group-dev" ]
    then
        echo "- 개발 환경에 배포를 시도합니다."
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

        echo "${IDLE_PROFILE}"
    fi

    ## 운영 환경일 경우
    if [ "$DEPLOYMENT_GROUP_NAME" == "h2h-code-deploy-group-prod" ]
    then
        echo "- 운영 환경에 배포를 시도합니다."
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

        echo "${IDLE_PROFILE}"
    fi
}

function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ "${IDLE_PROFILE}" == dev1 ] || [ "${IDLE_PROFILE}" == prod1 ]; then
      echo "8081"
    else
      echo "8082"
    fi
}