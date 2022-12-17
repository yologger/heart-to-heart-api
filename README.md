# Heart to Heart API Server
SNS 애플리케이션인 `Heart to Heart`의 API 서버입니다. 안드로이드 어플리케이션은 [이 곳](https://github.com/yologger/heart-to-heart-android)에서 확인할 수 있으며, 구현 내용은 다음과 같습니다.

## 인증 API 개발
- Gmail 인증 및 회원 가입 구현(`Spring Email`)
- Token 기반 로그인 및 접근 제어 구현(`JWT`, `Spring Security`) 
- Refresh Token을 통한 토큰 갱신 구현
- 로그아웃 구현
- 회원 탈퇴 구현

## Spring Validation

## 에러 처리
AOP`ControllerAdvice`를 통한 에러 처리 구현

## 게시글 API 개발
- 게시글 작성, 이미지 업로드 구현(`AWS S3`)
- 게시글 조회, Paging, Sorting 구현(`Spring Data JPA`, `Query DSL`)
- 게시글 삭제 구현

## TDD
- `JUnit 5`, `Truth`, `Assertion`, `Mockito`, `H2`를 사용한 단위테스트, 통합테스트 구현
- JACOCO

## 문서화
`Swagger 2`를 사용한 문서화


## 인프라 구축

### local 환경
`local` 환경에서는 별도의 데이터베이스 구축 없이도 어플리케이션을 실행할 수 있도록 인메모리 데이터베이스를 사용했습니다.
- H2
- Embedded Redis

### staging 환경
기존에는 SaaS인 `Heroku`를 사용하여 인프라를 구축했습니다.
- ~~Heroku Serverless Computing~~ (Deprecated)
- ~~Heroku MySQL~~ (Deprecated)

무중단 배포 구축을 위해서는 Linux 서버를 직접 제어할 필요가 있었습니다. 이 때문에 IaaS인 AWS EC2로 마이그레이션 했습니다.

또한 다음 기술을 사용하여 CI/CD 자동화를 구축했습니다.
- GitHub Actions
- AWS S3
- AWS CodeDeploy

리버스 프록시 서버로 `Nginx`를 배치하여 무중단 배포를 구축했습니다.

![](imgs/alpha.png)

### production 환경
Terraform을 사용하여 AWS VPC, Public Subnet, Private Subnet, Internet Gateway, NAT Gateway, Routing Table 구축을 자동화했습니다. 상세한 스크립트는 [이 곳](/terraform/production/aws_vpc.tf)에서 확인할 수 있습니다.

![](imgs/vpc.png)

Kubernetes AWS EKS(NodeGroup) 또한 Terraform으로 자동화했습니다. 상세한 스크립트는 [이 곳](/terraform/production/aws_eks.tf)에서 확인할 수 있습니다.

그 외에도 다음 기능을 구축했습니다.
- Kubernetes ReplicSet을 통한 스케일 아웃, 셀프 힐링 구축
- Kubernetes Deployment를 통한 무중단 배포, 롤백 기능 구축


## 로드 밸런싱 구축
AWS Elastic LoadBalancer(L7, ALB)와 Kubernetes Ingress 연동을 위해 AWS Load Balancer Controller를 사용하여 로드 밸런싱을 구축했습니다.

다음과 같이 AWS Load Balancer Controller가 정상적으로 작동하는 것을 확인할 수 있습니다.
```shell
$ kubectl get all -n kube-system
NAME                                                             READY   STATUS    RESTARTS   AGE
pod/aws-load-balancer-controller-649b9bc59c-fxvfd                1/1     Running   0          67m
pod/aws-load-balancer-controller-649b9bc59c-t6x8f                1/1     Running   0          67m
```


## CI/CD 자동화 구축
- 깃 브랜치 전략 도입 (develop, staging, main)
- GitHub Actions, Helm, AWS ECR, Argo CD를 통한 GitOps 기반 CI/CD 자동화 구축
- Slack 메시징 연동 구축

![](imgs/cicd.png)

Pull Request가 GitHub Repository main 브랜치에 병합되면 GitHub Actions가 자동으로 실행됩니다. 

빌드에서 성공하면 먼저 AWS ECR에 Docker Image를 Push 합니다.

그리고 Helm Chart를 업데이트 합니다.

쿠버네티스 클러스터에 Argocd Server가 실행 중인 것을 확인할 수 있습니다.
``` shell
$ kubectl get svc -n argocd
NAME                               TYPE           CLUSTER-IP       EXTERNAL-IP                                                                   PORT(S)                      AGE
argocd-application-controller      ClusterIP      172.20.22.79     <none>                                                                        8082/TCP                     76m
argocd-applicationset-controller   ClusterIP      172.20.150.239   <none>                                                                        7000/TCP                     76m
argocd-dex-server                  ClusterIP      172.20.47.109    <none>                                                                        5556/TCP,5557/TCP            76m
argocd-redis                       ClusterIP      172.20.26.106    <none>                                                                        6379/TCP                     76m
argocd-repo-server                 ClusterIP      172.20.186.182   <none>                                                                        8081/TCP                     76m
argocd-server                      LoadBalancer   172.20.78.125    ad234b32a27e646bcaacb712a03aee61-609176524.ap-northeast-2.elb.amazonaws.com   80:30144/TCP,443:30856/TCP   76m
```

ArgoCD는 업데이트된 Helm Chart를 읽어 Kuburnetes Cluster에 배포합니다.


## Auto Scaling 구축

CPU Utilization, Memory Usage, Network Traffic에 따른 오토 스케일링을 구축했습니다.

먼저 `HPA(Horizontal Pod AutoScaler)`, `Kubernetes Metrics Server`를 통해 팟 오토 스케일링을 구축했습니다.

그리고 `Cluster AutoScaler`를 통해 워커 노드 오토 스케일러 또한 구축했습니다.


## 모니터링 시스템 구축
Premetheus, Grafana로 모니터링
CPU Utilization, Memory Usage, Network Traffic
desired size

## 진행 중인 작업
현재 진행 중인 작업은 크게 세 가지입니다.

### 통합 로그 관리 시스템
EFK(Elasticsearch, Fluentbit, Kibana)를 통해 스프링 부트 어플리케이션의 로그 시스템을 구축했습니다.

### Global Cache
Redis를 통한 Global Cache 구축
현재 Replication, Cluster 구축 방법에 대해서는 정리한 상태
AWS ElasticCache 비용
Alert Manager

### 데이터베이스 다중화
Sharding
