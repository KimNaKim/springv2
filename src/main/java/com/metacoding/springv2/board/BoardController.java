package com.metacoding.springv2.board;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.metacoding.springv2._core.util.Resp;
import com.metacoding.springv2.user.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    // 모든 게시글 목록 보기 (DTO 응답)
    @GetMapping("/api/boards")
    public ResponseEntity<?> findAll() {
        List<BoardResponse.ListDTO> respDTO = boardService.findAll();
        return Resp.ok(respDTO);
    }

    // 게시글 상세보기 (DTO 응답)
    @GetMapping("/api/boards/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        BoardResponse.DetailDTO respDTO = boardService.findById(id);
        return Resp.ok(respDTO);
    }

    // 게시글 작성 (DTO 응답)
    @PostMapping("/api/boards")
    public ResponseEntity<?> save(@RequestBody BoardRequest.SaveDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO respDTO = boardService.save(requestDTO, sessionUser);
        return Resp.ok(respDTO);
    }

    // 게시글 수정 (DTO 응답)
    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody BoardRequest.UpdateDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO respDTO = boardService.update(id, requestDTO, sessionUser);
        return Resp.ok(respDTO);
    }

    // 게시글 삭제
    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.delete(id, sessionUser);
        return Resp.ok("게시글이 삭제되었습니다");
    }
}
