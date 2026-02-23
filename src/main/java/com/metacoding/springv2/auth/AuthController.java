package com.metacoding.springv2.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.metacoding.springv2._core.util.Resp;
import com.metacoding.springv2.user.UserRequest;
import com.metacoding.springv2.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    // 회원가입 요청을 처리하는 API
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AuthRequest.JoinDTO reqDto) {
        var respDTO = authService.회원가입(reqDto);
        return Resp.ok(respDTO);
    }

    // 로그인 요청을 처리하여 JWT 토큰을 반환하는 API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest.LoginDTO reqDto) { // JSON 데이터 파싱
        // 토큰 기반의 로그인을 수행하고 액세스 토큰을 생성함
        String accessToken = authService.login(reqDto);
        return Resp.ok(accessToken);
    }

    // 서버의 상태를 체크하는 헬스체크 API
    @GetMapping("/health")
    public String healthCheck() {
        return "health ok";
    }

    // 유저네임(아이디) 중복 여부를 체크하는 API
    @PostMapping("/api/check-username")
    public ResponseEntity<?> checkUsername(@RequestBody UserRequest.JoinDTO requestDTO) {
        userService.checkUsername(requestDTO.getUsername());
        return Resp.ok(true);
    }
}