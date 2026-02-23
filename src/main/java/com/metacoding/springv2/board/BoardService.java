package com.metacoding.springv2.board;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.metacoding.springv2._core.handler.ex.Exception403;
import com.metacoding.springv2._core.handler.ex.Exception404;
import com.metacoding.springv2.user.User;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // 모든 게시글 조회 (DTO 변환 후 반환)
    public List<BoardResponse.ListDTO> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(board -> new BoardResponse.ListDTO(board))
                .collect(Collectors.toList());
    }

    // 게시글 상세보기 (DTO 변환 후 반환)
    public BoardResponse.DetailDTO findById(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));
        return new BoardResponse.DetailDTO(board);
    }

    // 게시글 작성 (DTO 반환)
    @Transactional
    public BoardResponse.DetailDTO save(BoardRequest.SaveDTO requestDTO, User sessionUser) {
        Board board = boardRepository.save(requestDTO.toEntity(sessionUser));
        return new BoardResponse.DetailDTO(board);
    }

    // 게시글 수정 (DTO 반환)
    @Transactional
    public BoardResponse.DetailDTO update(Integer id, BoardRequest.UpdateDTO requestDTO, User sessionUser) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        // 권한 체크
        if (!board.getUser().getId().equals(sessionUser.getId())) {
            throw new Exception403("게시글을 수정할 권한이 없습니다");
        }

        board.update(requestDTO.getTitle(), requestDTO.getContent());
        return new BoardResponse.DetailDTO(board);
    }

    // 게시글 삭제
    @Transactional
    public void delete(Integer id, User sessionUser) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다"));

        // 권한 체크
        if (!board.getUser().getId().equals(sessionUser.getId())) {
            throw new Exception403("게시글을 삭제할 권한이 없습니다");
        }

        boardRepository.deleteById(id);
    }
}
