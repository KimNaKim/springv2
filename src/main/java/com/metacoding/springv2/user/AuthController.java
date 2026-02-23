package com.metacoding.springv2.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.metacoding.springv2._core.util.Resp;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final HttpSession session;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequest.JoinDTO requestDTO) {
        UserResponse.JoinDTO respDTO = userService.join(requestDTO);
        return Resp.ok(respDTO);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest.LoginDTO requestDTO) {
        String jwt = userService.login(requestDTO);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwt)
                .body(Resp.ok("로그인 성공"));
    }

    // 유저네임 중복체크
    @GetMapping("/api/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        userService.checkUsername(username);
        return Resp.ok("사용 가능한 유저네임입니다");
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        session.invalidate();
        return Resp.ok("로그아웃 되었습니다");
    }
}
