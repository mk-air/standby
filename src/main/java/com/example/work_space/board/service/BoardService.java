package com.example.work_space.board.service;

import com.example.work_space.board.dto.BoardRequestDto;
import com.example.work_space.board.dto.BoardResponseDto;
import com.example.work_space.board.dto.BoardUpdateRequestDto;
import com.example.work_space.board.dto.BoardUpdateResponseDto;

import java.util.List;

public interface BoardService {

    BoardResponseDto createBoard(BoardRequestDto requestDto);
    BoardResponseDto getBoard(Long boardId);
    List<BoardResponseDto> getBoards();
    BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto updateRequestDto);
    void deleteBoard(Long boardId);

}
