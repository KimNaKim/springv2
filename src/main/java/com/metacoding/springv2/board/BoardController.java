package com.metacoding.springv2.board;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metacoding.springv2._core.util.Resp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    // 모든 게시글 목록 보기 (DTO 응답)
    @GetMapping("/api/boards")
    public ResponseEntity<?> findAll() {
        List<BoardResponse.ListDTO> respDTO = boardService.findAll();
        return Resp.ok(respDTO);
    }
}
