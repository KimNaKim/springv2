package com.metacoding.springv2.user;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacoding.springv2._core.handler.ex.Exception400;
import com.metacoding.springv2._core.handler.ex.Exception404;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 유저를 찾을 수 없습니다: " + id));
    }

    public void checkUsername(String username) {
        Optional<User> userOP = userRepository.findByUsername(username);

        if (userOP.isPresent()) {
            throw new Exception400("이미 존재하는 유저네임입니다");
        }
    }
}