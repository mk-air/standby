package com.example.work_space.board.controller;

import com.example.work_space.board.dto.*;
import com.example.work_space.board.service.BoardService;
import com.example.work_space.constants.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @Valid @RequestPart BoardRequestDto requestDto,
            @RequestParam MultipartFile file) {
        BoardResponseDto board = boardService.createBoard(requestDto,file);
        return ResponseEntity.ok(new CommonResponse<>("보드 생성", board));
    }

    // 단건 조회
    @GetMapping("/{boardId}/members/{memberId}")
    public ResponseEntity<CommonResponse<BoardDetailResponseDto>> getBoard(
            @PathVariable Long boardId, @PathVariable Long memberId) {
        BoardDetailResponseDto board = boardService.getBoard(boardId, memberId);
        return ResponseEntity.ok(new CommonResponse<>("보드 조회 성공", board));
    }

    // 다건 조회
    @GetMapping("/{workSpaceId}")
    public ResponseEntity<CommonResponse<List<BoardResponseDto>>> getAllBoards(
            @PathVariable Long workSpaceId
    ) {
        List<BoardResponseDto> boards = boardService.getBoards(workSpaceId);
        return ResponseEntity.ok(new CommonResponse<>("보드 조회 성공", boards));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<CommonResponse<BoardUpdateResponseDto>> updateBoard(
            @PathVariable Long boardId, @RequestPart BoardUpdateRequestDto requestDto,
            @RequestPart MultipartFile file) {
        BoardUpdateResponseDto updateBoard = boardService.updateBoard(boardId, requestDto,file);
        return ResponseEntity.ok(new CommonResponse<>("보드 수정 성공", updateBoard));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<CommonResponse<Void>> deleteBoard(
            @PathVariable Long boardId, @RequestParam Long memberId) {
        boardService.deleteBoard(boardId, memberId);
        return ResponseEntity.ok(new CommonResponse<>("보드 삭제 완료"));
    }

}
