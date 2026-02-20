package com.metacoding.springv2.admin;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class AdminController {

    // cos만 접속가능한 함수
    @GetMapping("/admin/test")
    public String test() {
        return "<h1>관리자페이지</h1>";
    }

}
