# 👀 ColorfulWorld (색각이상자를 위한 혼동색 영역 분류 및 보정 웹서비스)
색각 이상자를 위한 혼동색 영역 분류 알고리즘 개발 및 색상 보정 서비스
- 색각이상자들의 색상인지를 위한 정확한 혼동색 분류
- 이질감 없는 이미지 색상 보정
- 웹 브라우를 사용한 접근성 증대
- 색각이상자들이 일상생활에서 발생하는 색상 관련 문제를 해결할 수 있도록 도와주는 것

## ⭐ 설명
### 개발 기간
- 2023.03 ~ 2023.06
### 개발 인원
- 4명(프론트 2명, 백 1명, AI 1명)

## ⭐ 발표 영상
👉[Colorful World 발표 영상](https://youtu.be/zbB5P4mDULg?feature=shared)👈

## ✨ ColorfulWorld의 주요 기능
### 이미지 업로드 및 결과 이미지 다운로드
- JPG, JPEG, PNG 확장자의 파일만 업로드 가능
- 색상이 변환된 이미지는 로컬로 다운로드 가능

### 색상 변환
- 이미지 내의 혼동색 판단
- 이미지 내의 인접한 색상이 같은 혼동선상에 있지 않도록 색상을 변환
  
### 만족도 평가
- 사용자로부터 색상이 변환된 결과 이미지에 대한 만족도 평가를 받음
- 만족으로 평가된 사진은 추후 딥러닝 모델 업데이트에 사용
  
## ⭐ 서비스 화면
### 시작 화면
<img width="300" src="https://github.com/Seohyun-0206/ColorfulWorld_Backend/assets/81468092/3f27253d-1ee0-4bd1-80af-a069e2892e8c">
<img width="300" src="https://github.com/Seohyun-0206/ColorfulWorld_Backend/assets/81468092/c6a65410-82c6-4657-ae2b-8df7f20cb6af">

### 회원가입 및 로그인
<img width="300" src="https://github.com/Seohyun-0206/ColorfulWorld_Backend/assets/81468092/a43a20ab-dda3-44c9-92bc-0f8cfaf88943">
<img width="300" src="https://github.com/Seohyun-0206/ColorfulWorld_Backend/assets/81468092/47ae7e36-3b34-42df-a0e8-fc583985fac9">

### 이미지 업로드
<img width="300" src="https://github.com/Seohyun-0206/ColorfulWorld_Backend/assets/81468092/b4f8ccda-2c8a-4ba8-9683-97c571892178">

### 결과페이지
<img width="300" src="https://github.com/Seohyun-0206/ColorfulWorld_Backend/assets/81468092/7c24c568-0f1d-41c8-8148-facd1747a2ed">

## 💻 개발 환경
- Backend
  - Intellij
  - java 17
  - Springboot 3.0.5
  - Springboot-jpa
  - Spring Security
  - MySQL
  - Redis
  - javamail
- Frontend
  - VSCode
  - JavaScript
  - React
  - Style Components
- 협업 툴
  - Figma(디자인)
  - Notion
  - Git & Github
- 배포
  - AWS EC2, RDS, S3
  - Docker
  - Nginx

## ✨ 기술
<img width="815" alt="딥러닝 모델" src="https://github.com/Seohyun-0206/ColorfulWorld_Backend/assets/81468092/189240e3-5ea7-4bac-be23-81c14853998a">

## ✨ E-R 다이어그램
<img width="376" alt="Untitled (1)" src="https://github.com/Seohyun-0206/ColorfulWorld_Backend/assets/81468092/13fe3666-c7eb-49f7-b327-94ac61c301ca">

## ✨ 클래스 다이어그램 & 시퀀스 다이어그램
<img width="800" src="https://github.com/Seohyun-0206/ColorfulWorld_Backend/assets/81468092/1ce35003-20ad-4c26-8f6f-34da818616aa">
<img width="800" src="https://github.com/Seohyun-0206/ColorfulWorld_Backend/assets/81468092/643faf47-977f-46dd-8822-7e7efee83469">


## 👨‍👩‍👧 팀원
- [전서현(본인, 백엔드)](https://github.com/Seohyun-0206)
  - 서비스에 필요한 기능 설계 및 DB 구축
  - Spring security, JWT, Redis를 이용한 로그인 기능 구현
  - 회원가입시 smtp를 이용한 메일 인증 기능 구현
  - 백엔드 색상 변환강도 저장 기능 구현
  - 결과 이미지 저장 기능 구현
  - AWS, Nginx, Docker를 이용한 클라우드 서버 구축 및 테스트

- [주다현(프론트엔드)](https://github.com/judahhh)

- [박규태(프론트엔드)](https://github.com/KyuTae98)

- [이규리(AI)](https://github.com/cu29635)

## ✊ 개발과정에서의 문제점
- 배포 과정에서의 문제점
  - 웹과 서버를 AWS를 통해 각각 배포했는데 https 통신시 인증서가 동일해야했음. 이 부분을 통합해 배포해야 했음
  - 서버, 웹, 딥러닝 서버를 연동하면서도 문제가 계속 발생해 문제 해결 어려움
- 딥러닝 서버 배포 문제점
  - 딥러닝 서버를 AWS에 올려 배포하는 과정에서 AWS 서버가 터지는 문제 발생
  - docker를 이용해 이미지화 시켜 AWS에 올렸지만 서버 과금이 발생해 운영을 지속하기 어려움
