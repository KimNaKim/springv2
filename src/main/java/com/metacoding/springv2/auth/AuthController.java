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

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody AuthRequest.JoinDTO reqDto) {
        var respDTO = authService.회원가입(reqDto);
        return Resp.ok(respDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest.LoginDTO reqDto) { // JSON 데이터 파싱
        // 토큰 기반
        String accessToken = authService.login(reqDto);
        return Resp.ok(accessToken);
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "health ok";
    }

    // 유저네임 중복 체크
    @PostMapping("/api/check-username")
    public Resp<?> checkUsername(@RequestBody UserRequest.JoinDTO requestDTO) {
        userService.checkUsername(requestDTO.getUsername());
        return new Resp<>(200, "성공", true);
    }
}