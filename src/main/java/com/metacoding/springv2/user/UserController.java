package com.metacoding.springv2.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.metacoding.springv2._core.util.Resp;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 유저 정보 보기
    @GetMapping("/api/users/{id}")
    public Resp<?> detail(@PathVariable Integer id) {
        User user = userService.findById(id);
        return new Resp<>(200, "성공", user);
    }
}
