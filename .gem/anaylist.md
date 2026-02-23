# 프로젝트 분석 보고서

## 1. 개요

`src` 폴더 내부의 소스 코드를 기반으로 분석된 프로젝트 현황입니다. Spring Boot와 Spring Security를 활용한 REST API 서버 구조를 갖추고 있습니다.

## 2. 기술 스택 및 설정

- **Framework**: Spring Boot
- **Security**: Spring Security (Stateless, JWT 인증 방식)
- **Database Access**: JPA (Repository 패턴 사용)
- **Utils**: Lombok, Jackson

## 3. 주요 기능 및 구현 현황

### A. 보안 (Security)

- **인증 방식**: JWT (Json Web Token) 기반 인증 (`JwtAuthorizationFilter`).
- **CORS**: `FilterConfig`를 통해 전역 필터로 설정됨 (`SecurityConfig` 내 설정은 주석 처리).
- **암호화**: `BCryptPasswordEncoder` 사용.
- **권한 관리**:
  - `/api/**`: 인증된 사용자 접근 가능.
  - `/admin/**`: ADMIN 권한 필요.
  - 예외 처리: 인증 실패(401) 및 권한 없음(403)에 대해 공통 JSON 응답 처리 (`RespFilter`).

### B. 유저 관리 (User)

- **DTO**: 회원가입(`JoinDTO`), 로그인(`LoginDTO`) 구조 정의됨.
- **API**:
  - `GET /api/users/{id}`: 유저 상세 정보 조회 (구현 완료).
- **Service**: 트랜잭션 관리 및 유저 조회 로직 구현.

### C. 공통 모듈

- **응답 처리**: `Resp` 객체와 `RespFilter`를 통해 일관된 JSON 응답 포맷 유지.

## 4. 구현 기능 체크리스트 (Agent1)

- [x] 회원가입된 유저 정보 보기 (`UserController`)
- [x] 유저네임 중복 체크 (`AuthController`)
- [x] 회원가입 및 로그인 기능 구현
