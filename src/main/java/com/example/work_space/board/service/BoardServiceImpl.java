package com.example.work_space.board.service;

import com.example.work_space.board.dto.*;
import com.example.work_space.board.entity.Board;
import com.example.work_space.board.repository.BoardRepository;
import com.example.work_space.files.entity.AttachFile;
import com.example.work_space.files.service.AttachFileService;
import com.example.work_space.workspace.entity.WorkSpace;
import com.example.work_space.workspace.repository.WorkSpaceRepository;
import com.example.work_space.workspace.type.WorkSpaceRole;
import com.example.work_space.workspace_member.entity.WorkSpaceMember;
import com.example.work_space.workspace_member.repository.WorkSpaceMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final WorkSpaceMemberRepository workSpaceMemberRepository;
    private final AttachFileService attachFileService;

    public BoardServiceImpl(BoardRepository boardRepository, WorkSpaceRepository workSpaceRepository, WorkSpaceMemberRepository workSpaceMemberRepository, AttachFileService attachFileService) {
        this.boardRepository = boardRepository;
        this.workSpaceRepository = workSpaceRepository;
        this.workSpaceMemberRepository = workSpaceMemberRepository;
        this.attachFileService = attachFileService;
    }

    @Transactional
    @Override
    public BoardResponseDto createBoard(BoardRequestDto requestDto, MultipartFile file) {
        WorkSpace workSpace = workSpaceRepository.findById(requestDto.getWorkspaceId()).orElseThrow();
        WorkSpaceMember workSpaceMember = workSpaceMemberRepository.findByWorkSpaceIdAndMemberId(requestDto.getWorkspaceId(),requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 워크 스페이스 회원을 찾을 수 없습니다."));

        validationOfRole(workSpaceMember);

        Board board = Board.builder()
                .workSpace(workSpace)
                .title(requestDto.getTitle())
                .color(requestDto.getColor())
                .info(requestDto.getInfo())
                .build();
        Board savedBoard = boardRepository.save(board);
        AttachFile imgFile = attachFileService.createImgFile(file);
        savedBoard.updateAttachFile(imgFile);
        imgFile.updateBoard(savedBoard);

        return new BoardResponseDto(savedBoard);

    }

    @Transactional(readOnly = true)
    @Override
    public BoardDetailResponseDto getBoard(Long boardId, Long memberId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다."));

        WorkSpaceMember workSpaceMember = workSpaceMemberRepository.findByWorkSpaceIdAndMemberId(board.getWorkSpace().getId(), memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 워크 스페이스 회원을 찾을 수 없습니다."));

        validationOfRole(workSpaceMember);

        return new BoardDetailResponseDto(board);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BoardResponseDto> getBoards(Long workSpaceId) {
        List<Board> boardList = boardRepository.findAllByWorkSpaceId(workSpaceId);

        return boardList.stream().map(BoardResponseDto::new).toList();
    }

    @Transactional
    @Override
    public BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto updateRequestDto) {
        WorkSpaceMember workSpaceMember = workSpaceMemberRepository.findByWorkSpaceIdAndMemberId(updateRequestDto.getWorkspaceId(),updateRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 워크 스페이스 회원을 찾을 수 없습니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드를 찾을 수 없습니다. ID: " + boardId));

        validationOfRole(workSpaceMember);

        // 엔티티 업데이트
        board.updateBoard(
                updateRequestDto.getTitle(),
                updateRequestDto.getColor(),
                updateRequestDto.getInfo()
        );

        // 수정된 엔티티 저장 후 응답 DTO 반환
        Board updateBoard = boardRepository.save(board);
        return new BoardUpdateResponseDto(updateBoard);
    }

    @Transactional
    @Override
    public void deleteBoard(Long boardId, Long memberId) {
        // 보드 존재 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다. ID: " + boardId));

        WorkSpaceMember workSpaceMember = workSpaceMemberRepository.findByWorkSpaceIdAndMemberId(board.getWorkSpace().getId(), memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 워크 스페이스 회원을 찾을 수 없습니다."));

        validationOfRole(workSpaceMember);

        // 보드 삭제
        boardRepository.delete(board);
    }

    // 권한 확인
    private static void validationOfRole(WorkSpaceMember workSpaceMember) {
        if(workSpaceMember.getRole().equals(WorkSpaceRole.READ_ONLY)){
            throw new IllegalArgumentException("ReadOnly 회원은 작성 할 수 없습니다.");
        }
    }

}
