package com.metacoding.springv2.user;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacoding.springv2._core.handler.ex.Exception400;
import com.metacoding.springv2._core.handler.ex.Exception401;
import com.metacoding.springv2._core.handler.ex.Exception404;
import com.metacoding.springv2._core.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserResponse.JoinDTO join(UserRequest.JoinDTO requestDTO) {
        // 유저네임 중복 체크
        checkUsername(requestDTO.getUsername());

        // 비밀번호 암호화
        String encPassword = bCryptPasswordEncoder.encode(requestDTO.getPassword());

        // 저장
        User user = userRepository.save(requestDTO.toEntity(encPassword));
        return new UserResponse.JoinDTO(user);
    }

    public String login(UserRequest.LoginDTO requestDTO) {
        // 유저 확인
        User user = userRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(() -> new Exception401("유저네임을 찾을 수 없습니다"));

        // 비밀번호 확인
        if (!bCryptPasswordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new Exception401("비밀번호가 일치하지 않습니다");
        }

        // JWT 토큰 생성
        return JwtUtil.create(user);
    }

    public UserResponse.DetailDTO findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 유저를 찾을 수 없습니다: " + id));
        return new UserResponse.DetailDTO(user);
    }

    public void checkUsername(String username) {
        Optional<User> userOP = userRepository.findByUsername(username);

        if (userOP.isPresent()) {
            throw new Exception400("이미 존재하는 유저네임입니다");
        }
    }
}