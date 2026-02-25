package com.metacoding.springv2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.metacoding.springv2.board.Board;
import com.metacoding.springv2.board.BoardRepository;
import com.metacoding.springv2.board.BoardService;
import com.metacoding.springv2.board.BoardResponse.BoardDTO;
import com.metacoding.springv2.board.BoardResponse.DetailDTO;
import com.metacoding.springv2.board.BoardResponse.SaveDTO;
import com.metacoding.springv2.user.User;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Test
    public void 게시글목록_test() {
        // given
        User user = User.builder().id(1).build();
        List<Board> boards = new ArrayList<>();
        boards.add(Board.builder().id(1).title("제목1").content("내용1").user(user)
                .createdAt(Timestamp.valueOf(LocalDateTime.now())).build());
        boards.add(Board.builder().id(2).title("제목2").content("내용2").user(user)
                .createdAt(Timestamp.valueOf(LocalDateTime.now())).build());
        System.out.println("boards : " + boards);

        // stub
        when(boardRepository.findAll()).thenReturn(boards);

        // when
        List<BoardDTO> boardDTOs = boardService.게시글목록();
        System.out.println("boardDTOs : " + boardDTOs);

        // then
        assertThat(boardDTOs.size()).isEqualTo(2);
        assertThat(boardDTOs.get(0).getTitle()).isEqualTo("제목1");
        assertThat(boardDTOs.get(1).getTitle()).isEqualTo("제목2");
    }

    @Test
    public void 게시글상세_test() {
        // given
        int id = 1;
        int sessionUserId = 1;
        User user = User.builder().id(1).username("ssar").build();
        Board board = Board.builder().id(1).title("제목").content("내용").user(user)
                .createdAt(Timestamp.valueOf(LocalDateTime.now())).build();
        System.out.println("board : " + board);

        // stub
        when(boardRepository.findByIdWithUser(id)).thenReturn(Optional.of(board));

        // when
        DetailDTO detailDTO = boardService.게시글상세(id, sessionUserId);
        System.out.println("detailDTO : " + detailDTO);

        // then
        assertThat(detailDTO.getTitle()).isEqualTo("제목");
        assertThat(detailDTO.getContent()).isEqualTo("내용");
        assertThat(detailDTO.getUsername()).isEqualTo("ssar");
        assertThat(detailDTO.getIsBoardOwner()).isTrue();
    }

    @Test
    public void 게시글작성_test() {
        // given
        User user = User.builder().id(1).build();
        Board board = Board.builder().id(1).title("제목").content("내용").user(user)
                .createdAt(Timestamp.valueOf(LocalDateTime.now())).build();
        System.out.println("board : " + board);

        // stub
        when(boardRepository.save(any())).thenReturn(board);

        // when
        SaveDTO saveDTO = boardService.게시글작성(board);
        System.out.println("saveDTO : " + saveDTO);

        // then
        assertThat(saveDTO.getTitle()).isEqualTo("제목");
    }
}
