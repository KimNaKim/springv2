package com.metacoding.springv2.reply;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.metacoding.springv2._core.util.Resp;
import com.metacoding.springv2.user.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ReplyController {

    private final ReplyService replyService;
    private final HttpSession session;

    // 댓글 작성
    @PostMapping("/api/replies")
    public ResponseEntity<?> save(@RequestBody ReplyRequest.SaveDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ReplyResponse.DTO respDTO = replyService.save(requestDTO, sessionUser);
        return Resp.ok(respDTO);
    }

    // 댓글 삭제
    @DeleteMapping("/api/replies/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.delete(id, sessionUser);
        return Resp.ok("댓글이 삭제되었습니다");
    }
}
