package com.example.work_space.board.service;

import com.example.work_space.board.dto.BoardRequestDto;
import com.example.work_space.board.dto.BoardResponseDto;
import com.example.work_space.board.dto.BoardUpdateRequestDto;
import com.example.work_space.board.dto.BoardUpdateResponseDto;
import com.example.work_space.board.entity.Board;
import com.example.work_space.board.repository.BoardRepository;
import com.example.work_space.workspace.entity.WorkSpace;
import com.example.work_space.workspace.repository.WorkSpaceRepository;
import com.example.work_space.workspace.service.WorkSpaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardSeriviceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final WorkSpaceRepository workSpaceRepository;

    public BoardSeriviceImpl(BoardRepository boardRepository, WorkSpaceRepository workSpaceRepository) {
        this.boardRepository = boardRepository;
        this.workSpaceRepository = workSpaceRepository;
    }

    @Transactional
    @Override
    public BoardResponseDto createBoard(BoardRequestDto requestDto) {
        if(requestDto.getTitle() == null) {
            throw new IllegalArgumentException("보드 제목을 작성해주세요.");
        }

        WorkSpace workSpace = workSpaceRepository.findById(requestDto.getWorkspaceId()).orElseThrow();

        Board board = Board.builder()
                .workSpace(workSpace)
                .title(requestDto.getTitle())
                .color(requestDto.getColor())
                .img(requestDto.getImg())
                .info(requestDto.getInfo())
                .build();
        Board savedBoard = boardRepository.save(board);

        return new BoardResponseDto(savedBoard, requestDto.getWorkspaceId());

    }

    @Transactional
    @Override
    public BoardResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));

        return new BoardResponseDto(board);
    }

    @Override
    public List<BoardResponseDto> getBoards() {
        return List.of();
    }

    @Transactional
    @Override
    public BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto updateRequestDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다. ID: " + boardId));

        // 엔티티 업데이트
        board.updateBoard(
                updateRequestDto.getTitle(),
                updateRequestDto.getColor(),
                updateRequestDto.getImg(),
                updateRequestDto.getInfo()
        );

        // 수정된 엔티티 저장 후 응답 DTO 반환
        Board updateBoard = boardRepository.save(board);
        return new BoardUpdateResponseDto(updateBoard);
    }

    @Transactional
    @Override
    public void deleteBoard(Long boardId) {

        // 보드 존재 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다. ID: " + boardId));

        // 보드 삭제
        boardRepository.delete(board);

    }

}
