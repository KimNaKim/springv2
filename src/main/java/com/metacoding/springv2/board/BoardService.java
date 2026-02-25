package com.metacoding.springv2.board;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metacoding.springv2._core.handler.ex.Exception404;
import com.metacoding.springv2.board.BoardResponse.BoardDTO;
import com.metacoding.springv2.board.BoardResponse.DetailDTO;
import com.metacoding.springv2.board.BoardResponse.SaveDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // select 할 때만 lazyloaing 관련 오류가 뜨는 것 방지
public class BoardService {
    public final BoardRepository boardRepository;

    // 1. DTO를 이용한 게시글 목록보기
    public List<BoardDTO> 게시글목록() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(board -> new BoardDTO(board)).toList();
    }

    // 2. DTO를 이용한 게시글 상세보기
    public DetailDTO 게시글상세(Integer id, int sessionUserId) {
        Board board = boardRepository.findByIdWithUser(id) // 직접 fetch join
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없어요."));
        return new DetailDTO(board, sessionUserId);
    }

    @Transactional // 오버라이드
    public SaveDTO 게시글작성(Board board) {
        SaveDTO saveDTO = new SaveDTO(boardRepository.save(board));
        return saveDTO;
    }

    @Transactional // 오버라이드
    public void 게시글수정() {

    }

    @Transactional // 오버라이드
    public void 게시글삭제() {

    }

}
