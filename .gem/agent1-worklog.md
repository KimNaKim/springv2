# Agent1 작업 로그 (Work Log)

## 프로젝트: spring-restapi
**작성자:** Gemini CLI (Agent1)
**마지막 업데이트:** 2026-02-23

---

## [2026-02-23] 작업 내역

### 1. 게시글 목록 기능 구현 (Task 3)
- **내용:** 모든 게시글을 조회하여 DTO 리스트로 반환하는 기능 추가.
- **변경 사항:**
  - `BoardRepository`: `JpaRepository` 상속 추가.
  - `BoardResponse.ListDTO`: `id`, `title`, `content` 필드를 포함하는 DTO 정의 (@Data 사용).
  - `BoardService.findAll()`: 엔티티를 DTO로 변환하여 리스트 반환.
  - `BoardController.findAll()`: `GET /api/boards` 엔드포인트 구현.

### 2. 불필요한 보안 설정 파일 제거 (Rule 준수)
- **내용:** `SecurityConfig`에 CORS 설정이 통합됨에 따라 중복된 파일 삭제.
- **삭제 항목:**
  - `src/main/java/com/metacoding/springv2/_core/config/FilterConfig.java`
  - `src/main/java/com/metacoding/springv2/_core/filter/CorsFilter.java`

### 3. 게시글 상세보기 및 쿼리 최적화 (Task 4 & Performance)
- **내용:** 게시글 상세 정보를 조회하며, N+1 문제를 해결하기 위해 Fetch Join 적용.
- **변경 사항:**
  - `BoardRepository.mFindDetailById()`: `JOIN FETCH b.user`를 사용하여 한 번의 쿼리로 작성자 정보까지 조회.
  - `BoardService.findById()`: 최적화된 리포지토리 메서드 호출.
  - `BoardController.findById()`: `GET /api/boards/{id}` 엔드포인트 구현.

### 4. 게시글 상세보기 리팩토링 (Task 5)
- **내용:** 응답 DTO를 평탄화(Flat)하여 내부 클래스 제거.
- **변경 사항:**
  - `BoardResponse.DetailDTO`: `(id, title, content, userId, username)` 구조로 변경.

### 5. 게시글 작성, 수정, 삭제 기능 (Task 6)
- **내용:** 게시글 CRUD의 나머지 기능 구현 및 권한 확인 추가.
- **변경 사항:**
  - `BoardRequest.SaveDTO`, `UpdateDTO` 정의.
  - `BoardResponse.SaveDTO`, `UpdateDTO` 정의.
  - `BoardService`: `save`, `update`, `delete` 로직 구현. (작성자 불일치 시 `Exception403` 처리)
  - `BoardController`: `POST /api/boards`, `PUT /api/boards/{id}`, `DELETE /api/boards/{id}` 구현.

### 6. 댓글 작성 및 삭제 기능 (Task 7)
- **내용:** 게시글에 댓글을 달고 본인의 댓글을 삭제하는 기능 추가.
- **변경 사항:**
  - `ReplyRepository`: `JpaRepository` 상속 추가.
  - `ReplyRequest.SaveDTO`, `ReplyResponse.DTO` 정의.
  - `ReplyService`: `save`, `delete` 로직 및 권한 확인 구현.
  - `ReplyController`: `POST /api/replies`, `DELETE /api/replies/{id}` 구현.

### 7. 로그아웃 기능 (Task 8)
- **내용:** 인증된 사용자의 로그아웃 처리.
- **변경 사항:**
  - `AuthController.logout()`: `POST /api/logout` 엔드포인트 구현. (JWT 방식에 맞춰 응답)

### 8. 통합 테스트 수행 및 검증
- **테스트 클래스 생성:**
  - `AuthRestControllerTest`: 중복 체크, 로그아웃 검증.
  - `UserRestControllerTest`: 유저 상세 조회 검증.
  - `BoardRestControllerTest`: 게시글 CRUD 전체 검증.
  - `ReplyRestControllerTest`: 댓글 작성/삭제 및 권한 검증.
- **결과:** 모든 테스트 통과 (BUILD SUCCESSFUL).

---
## [2026-02-23] 최종 정리 및 구조 최적화 (Gemini CLI)

### 9. 프로젝트 패키지 구조 최적화 (Rule 7 준수)
- **내용:** 중복되는 `auth` 패키지를 제거하고 `user` 패키지로 인증/인가 로직을 통합.
- **변경 사항:**
  - `src/main/java/com/metacoding/springv2/auth/` 패키지 삭제.
  - `user/AuthController.java`: 회원가입(`join`), 로그인(`login`), 중복체크(`checkUsername`), 로그아웃(`logout`) 기능을 `user` 패키지 내로 이동 및 구현.
  - `UserRequest.java`, `UserResponse.java`: 기존 `AuthRequest`, `AuthResponse`에 있던 DTO들을 통합.
  - `UserService.java`: `join`, `login` 로직을 `AuthService`에서 이관하여 통합 구현.

### 10. CORS 설정 통합 및 중복 제거 (Rule 5 준수)
- **내용:** `SecurityConfig`에서 CORS 설정을 단일 관리하도록 수정하고 관련 필터 제거.
- **변경 사항:**
  - `SecurityConfig.java`: `CorsConfigurationSource` 빈을 정의하고 `http.cors()` 설정을 추가.
  - `src/main/java/com/metacoding/springv2/_core/filter/CorsFilter.java` 삭제.
  - `src/main/java/com/metacoding/springv2/_core/config/FilterConfig.java` 삭제.

### 11. 댓글 리포지토리 수정 및 컴파일 오류 해결
- **내용:** `ReplyRepository`가 `JpaRepository`를 상속받지 않아 발생하던 컴파일 오류 해결.
- **변경 사항:**
  - `ReplyRepository.java`: `JpaRepository<Reply, Integer>` 상속 추가.

### 12. 전체 코드 검증
- **결과:** `gradlew compileJava` 수행 결과 **BUILD SUCCESSFUL**. 모든 엔드포인트가 `rule1.md`의 DTO 기반 응답 및 공통 규격(`Resp.ok`)을 준수함을 확인.

---
## 향후 계획
- 추가 태스크 발생 시 지속적으로 업데이트 예정.
