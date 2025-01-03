package com.example.work_space.board;

import com.example.work_space.board.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    BoardResponseDto createBoard(BoardRequestDto requestDto, MultipartFile file);
    BoardDetailResponseDto getBoard(Long boardId, Long memberId);
    List<BoardResponseDto> getBoards(Long workSpaceId);
    BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto updateRequestDto, MultipartFile file);
    void deleteBoard(Long boardId, Long memberId);

}
