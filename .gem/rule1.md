1. 시큐리티에서 CORS 필터를 적용할 것

2. 회원가입된 유저 정보 보기 기능이 필요하니 추가할 것
- UserContoller에 /api/users/{id} 형태로 만들기
- 메서드는 AuthController를 참고해서 동일 패턴으로 만들기
- DTO는 @Data를 사용하기
- 추가된 소스코드에 친절하고 쉬운 주석 달기

3. 예외 종류를 구별하기
- 400 오류 (Exception400)
- 인증 체크 실패 (Exception401)
- 권한 체크 실패 (Exception403)
- 페이지가 존재하지 않음 (Exception404)
- 위 오류들을 제외한 서버 오류 (Exception500)

4.
