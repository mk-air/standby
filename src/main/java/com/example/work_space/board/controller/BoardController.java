package com.example.work_space.board.controller;

import com.example.work_space.board.dto.BoardRequestDto;
import com.example.work_space.board.dto.BoardResponseDto;
import com.example.work_space.board.dto.BoardUpdateRequestDto;
import com.example.work_space.board.dto.BoardUpdateResponseDto;
import com.example.work_space.board.service.BoardService;
import com.example.work_space.constants.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<BoardResponseDto>> createBoard(
            @RequestBody BoardRequestDto requestDto) {
        BoardResponseDto board = boardService.createBoard(requestDto);
        return ResponseEntity.ok(new CommonResponse<>("보드 생성", board));
    }

    // 단건 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<CommonResponse<BoardResponseDto>> getBoard(
            @PathVariable Long boardId) {
        BoardResponseDto board = boardService.getBoard(boardId);
        return ResponseEntity.ok(new CommonResponse<>("보드 조회 성공", board));
    }

    // 다건 조회
    @GetMapping
    public ResponseEntity<CommonResponse<List<BoardResponseDto>>> getAllBoards() {
        List<BoardResponseDto> boards = boardService.getBoards();
        return ResponseEntity.ok(new CommonResponse<>("보드 조회 성공", boards));
    }

    // 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<CommonResponse<BoardUpdateResponseDto>> updateBoard(
            @PathVariable Long boardId, @RequestBody BoardUpdateRequestDto requestDto) {
        BoardUpdateResponseDto updateBoard = boardService.updateBoard(boardId, requestDto);
        return ResponseEntity.ok(new CommonResponse<>("보드 수정 성공", updateBoard));
    }

    // 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<CommonResponse<Void>> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok(new CommonResponse<>("보드 삭제 완료"));
    }

}
