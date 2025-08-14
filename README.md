# KakaoTech Bootcamp : 해커톤 대회

## 팀원 구성 및 역할

| 이름 | 직무 | 역할 |
| --- | --- | --- |
| 🙇‍♂ noah.kim(김지호) | 팀장<br>풀스택 / 프론트엔드 | - 카카오지도 기반 전체 서비스 제작<br>- 피그마 기반 서비스 디자인 |
| mumu.park(박성춘) | 풀스택 / 백엔드 | - springboot 기반 API 서버 개발 및 데이터베이스 구축 |
| kane.park(박건) | 풀스택 / 백엔드 | - FastAPI 기반 AI 서버 구축 및 개발 |
| yuna.lee(이유나) | 클라우드 <br>(Devops) | - 서비스 아키텍쳐 설계 및 구축 |
| noah.kim(김다현) | AI | - 챗봇 AI 서비스 개발 및 파인튜닝 |
| joy.yoon(윤지원) | AI | - 데이터 크롤링 챗봇 AI 서비스 개발 |
<br>

## 진행기간 
25.02.26. (수) ~ 25.02.28 (금) - 총 3일

## 목차 <a name = "index"></a>

### [1. 서비스 소개](#introduce)
### [2. 아키텍쳐 및 구현 내용](#project)
### [3. 결과](#result)
### [4. 회고](#review)



## 1. 서비스 소개 <a name = "introduce"></a>

카카오테크 부트캠프 주변의 맛집을 소개하고, 같이 식사할 수 있는 모임인 ‘밥팟’을 모집하고 참여할 수 있는 지도 기반 서비스

### [발표 자료 보러가기](https://docs.google.com/presentation/d/1nALvVPgiVQ4iKfs8G17iWEFI5H2hjbmx/edit?usp=drive_link&ouid=103722667745901978766&rtpof=true&sd=true)

- 스택 : SpringBoot3, Java21, MySQL:9.1.0, JPA, QueryDSL:5.0.0, Redis

### 1.1 메인 화면

<img width="788" height="543" alt="image" src="https://github.com/user-attachments/assets/11d3dd31-213c-47d1-9051-cd832665a1d9" />


<img width="788" height="543" alt="image" src="https://github.com/user-attachments/assets/1803845b-74c1-4277-baec-900b1b17a89e" />

<br>
<br>

## 1.2 밥팟 생성화면

<img width="806" height="518" alt="image" src="https://github.com/user-attachments/assets/2a57eb7d-f8c6-484d-a089-43d0fb8596b0" />


<img width="806" height="518" alt="image" src="https://github.com/user-attachments/assets/5118931b-cdf5-4abd-9584-439f4afc3223" />


## 2. 아키텍처 및 구현 내용 <a name = "project"></a>

[서비스 아키텍처]
<img width="941" height="381" alt="image" src="https://github.com/user-attachments/assets/1a14a2cd-5940-41c5-aeb4-64193b88f033" />


**[구현 내용]**

- 밥팟 생성 및 참여, 삭제 기능
- 회원가입 및 로그인, 토큰 재발급 기능
- 회원 정보 조회 기능
- 식당 조회 기능
- 정산 정보 조회 기능


<br>

## 3. 🥈 결과 <a name = "result"></a> 

### 본상 수상 🎉🎉
<img width="300" height="600" alt="image" src="https://github.com/user-attachments/assets/9925fce8-6871-49a8-adb5-92a5e3b55594" />

## 4. ✏️ 회고 <a name = "review"></a>
[블로그 글 보러가기](https://choons.tistory.com/70)

지금까지 해왔던 해커톤중 가장 재미있게 참여한 해커톤입니다.

단순 API 서버개발이 끝이 아닌 도메인을 구매하고, HTTPS 적용까지 마친 후 **프론트**, **springboot 서버**, **Fast API 서버**를 배포하는 과정을 모두 경험하였는데요. 많은 시행착오도 있었지만 배운점이 정말 많았습니다.

다양한 분야의 사람들과 협업을 하는 과정 속에서 개발 지식뿐 아니라 **소프트 스킬**의 중요성 또한 크게 느끼게 되었습니다.

그리고 **좋은 개발자**란 객체지향, 클린코드 만을 지향해서 훌륭한 코드만을 따라가는게 아닌 주어진 시간안에 최선의 결과물을 만들어내어 성공적인 결과물을 만들어내는 사람이라고 느꼈습니다.
물론, 빠른 시간안에 객체지향과 클린코드를 모두 적용할 수 있다면 더더욱 좋겠지만 이는 평생의 숙제라고 생각하고 있습니다.
