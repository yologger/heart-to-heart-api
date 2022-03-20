# Heart to Heart API Server
이 프로젝트는 `Heart to Heart` 어플리케이션을 위한 API 서버입니다. `Heart to Heart` 안드로이드 어플리케이션은 [이 곳](https://github.com/yologger/heart-to-heart-android)에서 확인할 수 있습니다.

## 기술 스택

### 공통
- Spring Boot
- Spring Data JPA
- Spring Security
- Spring Validation
- Spring Mail
- CI/CD pipeline (GitHub Actions/AWS CodeDeploy)

### 개발 환경
- Heroku
- Heroku Jaws DB (MySQL DB)
- AWS S3

### 테스트 환경
- AWS EC2
- AWS S3
- AWS RDS (Maria DB)

### 운영 환경
- AWS EC2
- AWS S3
- AWS RDS (Maria DB)

# Todo List
- [x] 회원가입 시 이메일 인증 구현
- [x] CI/CD pipeline 구축
- [ ] 이미지 업로드 시 최적화
- [x] JWT 기반 OAuth2 인증 구현 (Access Token, Refresh Token)
- [ ] 팔로우, 팔로잉 기능 구현
- [ ] 비밀번호 변경 구현
- [ ] 글 삭제 구현
- [ ] 깃 브랜치 전략 도입(`git-flow`)
- [ ] 테스트 환경 - `Nginx`를 통한 무중단 배포 구현
- [ ] 운영 환경 - 컨테이너 오케스트레이션를 위해 `Docker`, `Kubernetes` 환경으로 마이그레이션