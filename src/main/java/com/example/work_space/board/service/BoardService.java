package com.example.work_space.board.service;

import com.example.work_space.board.dto.*;

import java.util.List;

public interface BoardService {

    BoardResponseDto createBoard(BoardRequestDto requestDto);
    BoardDetailResponseDto getBoard(Long boardId, Long memberId);
    List<BoardResponseDto> getBoards(Long workSpaceId);
    BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto updateRequestDto);
    void deleteBoard(Long boardId, Long memberId);

}
