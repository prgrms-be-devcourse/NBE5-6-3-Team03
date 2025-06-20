# ✈️ TripIn
<div align="center">
<img width="250" alt="tripinlogo" src="https://github.com/user-attachments/assets/e3334965-78ee-4608-95f0-68d6761cfa3a" />
<br>
TripIn은 <strong>여행 계획부터 정산까지 한 번에 관리할 수 있는 여행 일정 관리 플랫폼</strong>입니다. <br>
사용자는 개인의 여행 계획을 등록하고 여행 일정을 직접 편집할 수 있습니다. <br>
여행 후에는 공금/지출 내역을 바탕으로 정산 내역을 메일로 발송하고 환율을 적용하여 잔금/추가금 분배가 가능합니다.
</div>

<br/>
<br/>

# ✨ 팀원 소개
| PM | TM | TM | TM | TM | 
|:------:|:------:|:------:|:------:|:------:|
| <img src="https://avatars.githubusercontent.com/u/203729461?v=4" alt="박현서" width="130"> | <img src="https://avatars.githubusercontent.com/u/90251939?v=4" alt="김도연" width="130"> | <img src="https://avatars.githubusercontent.com/u/144788790?v=4" alt="김도윤" width="130"> | <img src="https://avatars.githubusercontent.com/u/155226507?v=4" alt="김준형" width="130"> | <img src="https://avatars.githubusercontent.com/u/84299665?v=4" alt="조희제" width="130"> |
| [박현서](https://github.com/hyeunS-P) | [김도연](https://github.com/pia01190) | [김도윤](https://github.com/doyun-a) | [김준형](https://github.com/NoviceWyatt19) |  [조희제](https://github.com/Tokwasp) |
| 환율, LLM, 메일 서버 | 여행 일정, 정산, 배포 | 여행 계획, LLM | 관리자, 지도 | 회원, 정산, 프론트 |

<br/>
<br/>

# 📅 프로젝트 개발 기간
2025.06.11 ~ 2025.06.18

<br/>
<br/>

# 🎯 3차 프로젝트 목표
2차 프로젝트 버전업 [[2차 프로젝트 보러가기](https://github.com/prgrms-be-devcourse/NBE5-6-2-Team03)]
<br>
### [기능 추가]
- AI 기반 계획 추천 기능
- 여행 계획 생성 시 화폐 단위 설정 기능
- 정산 시 원화와 외화 함께 표시
### [고도화]
- 여행 일정 및 지출 도메인 통합
- 메일 발송 서버 코틀린 기반 마이그레이션
- 프론트엔드 Thymeleaf → React 전환
- 배포 환경 구성 (프론트엔드 - Vercel, 백엔드 - AWS)

<br/>
<br/>

# 🛠 기술 스택
<p>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
<img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
<img src="https://img.shields.io/badge/jpa-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/querydsl-0769AD?style=for-the-badge&logo=springboot&logoColor=white">
</p>
<p>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/langchain4j-1C3C3C?style=for-the-badge&logo=langchain&logoColor=white">
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">
</p>
<p>
<img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=white">
<img src="https://img.shields.io/badge/typescript-3178C6?style=for-the-badge&logo=typescript&logoColor=white">
<img src="https://img.shields.io/badge/vercel-000000?style=for-the-badge&logo=vercel&logoColor=white">
</p>


<br/>
<br/>

# 🌟 기능 소개
## [ 회원 ]
- 회원가입
- 로그인 및 로그아웃
- 회원 정보 조회
- 임시 비밀번호 메일 발송


## [ 관리자 ]
- 회원 조회 및 수정, 삭제

## [ 여행 계획 ]
- 여행 계획 등록 및 수정, 삭제
- AI 여행 계획 추천 받기

## [ 여행 일정 ]
- 여행 일정 등록 및 수정, 삭제
- AI 여행 일정 예상 시간

## [ 정산 ]
- 공금, 지출에 따른 정산
- 정산 내역 메일 발송

## [ 환율 ]
- 정산 시 환율 평균 비교 안내
- 잔금/추가금 배분 시 환율 적용

<br/>

| 임시 비밀번호 발급 | 관리자 회원 조회 |
|:------:|:------:|
| <img src="https://github.com/user-attachments/assets/a0faeb89-38ff-497e-a2f7-274c8bd894bf" width="350"> | <img src="https://github.com/user-attachments/assets/96a3730a-8760-48bf-b877-ba6eec89cc08" width="350"> |
| 여행 계획 등록 | AI 기반 여행 추천 |
| <img src="https://github.com/user-attachments/assets/b19a48f7-1575-4346-8699-c687a5695627" width="350"> | <img src="https://github.com/user-attachments/assets/333aabb3-11de-456a-82ae-d3ccf807bb52" width="350"> |
| 여행 일정 등록 | 정산 |
| <img src="https://github.com/user-attachments/assets/01f1991e-7302-438a-97a2-953334762653" width="350"> | <img src="https://github.com/user-attachments/assets/871381fe-3b43-40ce-ac9d-1afdd957d3c1" width="350"> |


<br/>
<br/>

# 🔧 System Architecture

![시스템구성도 drawio](https://github.com/user-attachments/assets/d8294656-289a-4f1c-9862-609ed9111010)


<br/>
<br/>

# ☁️ ERD

![NBE5-6-2-Team03](https://github.com/user-attachments/assets/2f2a3d2a-daa6-4fc8-a73a-5af6228385b6)

<br/>
<br/>

# ✏️ API 명세서

[[API 명세서 보러가기]](https://www.notion.so/API-218cf6c02f5081c69d4bc21a940ad997?source=copy_link)


<br/>
<br/>

# 📖 UI Flow Chart

<img width="3781" alt="uiflowchart" src="https://github.com/user-attachments/assets/8f1e536d-3435-4601-b3e9-87a1377f397f" />

<br/>
<br/>

# 📌 Git Commit Convention

일관성 있는 커밋 메시지를 위해 아래 Git 커밋 컨벤션을 따릅니다.

👉 [Git Commit Convention 바로가기](https://github.com/prgrms-be-devcourse/NBE5-6-2-Team03/wiki/%F0%9F%8C%B1-Git-Commit-Convention)
