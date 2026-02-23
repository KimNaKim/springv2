package com.metacoding.springv2.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.metacoding.springv2._core.util.Resp;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 특정 유저의 상세 정보를 조회하는 API
    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        // 서비스 계층에서 유저 정보를 조회함
        UserResponse.DetailDTO respDTO = userService.findById(id);
        return Resp.ok(respDTO);
    }
}
