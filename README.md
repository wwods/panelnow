# PanelNow — 설문조사 웹 플랫폼

설문 생성·참여·통계 조회와 포인트 기반 보상 시스템을 갖춘 풀스택 설문조사 웹 애플리케이션.  
Java Servlet MVC 패턴 기반으로 구현.

## Features

- 회원가입·로그인·세션 관리, 관리자/일반 회원 권한 분리
- 설문 생성·수정·삭제 (객관식·주관식 질문 및 보기 관리)
- 설문 참여, 중복 참여 방지
- 객관식 보기별 응답 수 집계 및 통계 시각화
- 포인트 적립·이력 관리·상품 구매 흐름

## Architecture

```
Controller (Servlet)
    ├── AuthServlet          회원가입 / 로그인
    ├── SurveyController     설문 목록 / 참여 / 응답
    ├── AdminController      관리자 대시보드
    ├── ProductController    상품 목록 / 구매
    └── StatisticsServlet    응답 통계

DAO
    ├── MemberDAO / UserDAO
    ├── SurveyDAO / QuestionDAO / ChoiceDAO
    ├── ResponseDAO
    ├── ProductDAO
    └── StatisticsDAO

View (JSP)
    ├── auth/        login.jsp, register.jsp
    ├── survey/      list, form, manage, stats
    ├── product/     list, add
    └── user/        mypage
```

## Database

8개 테이블: `회원, 설문, 질문, 보기, 응답, 응답상세, 상품, 구매, 포인트이력`

## Tech Stack

- Java, Servlet, JSP, JSTL
- MySQL, JDBC
- Apache Tomcat

## Course

한신대학교 웹 프로그래밍 기말 프로젝트
