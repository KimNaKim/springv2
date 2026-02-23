package com.metacoding.springv2.reply;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacoding.springv2._core.handler.ex.Exception403;
import com.metacoding.springv2._core.handler.ex.Exception404;
import com.metacoding.springv2.board.Board;
import com.metacoding.springv2.board.BoardRepository;
import com.metacoding.springv2.user.User;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ReplyResponse.DTO save(ReplyRequest.SaveDTO requestDTO, User sessionUser) {
        Board board = boardRepository.findById(requestDTO.getBoardId())
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        Reply reply = replyRepository.save(requestDTO.toEntity(sessionUser, board));
        return new ReplyResponse.DTO(reply);
    }

    @Transactional
    public void delete(Integer id, User sessionUser) {
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new Exception404("댓글을 찾을 수 없습니다"));

        if (!reply.getUser().getId().equals(sessionUser.getId())) {
            throw new Exception403("댓글을 삭제할 권한이 없습니다");
        }

        replyRepository.deleteById(id);
    }
}
