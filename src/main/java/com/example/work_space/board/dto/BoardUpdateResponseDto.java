package com.example.work_space.board.dto;

import com.example.work_space.board.entity.Board;
import com.example.work_space.list.dto.ListResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardUpdateResponseDto {

    private Long workspaceId; // 워크스페이스 ID
    private Long id;
    private String title; // 보드 이름
    private String color; // 보드 배경색
    private Long imgId; // 보드 이미지 ID
    private String info; // 보드 설명
    private List<ListResponseDto> lists;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BoardUpdateResponseDto(Board board) {
        this.workspaceId = board.getWorkSpace().getId();
        this.id = board.getId();
        this.title = board.getTitle();
        this.color = board.getColor();
        this.info = board.getInfo();
        this.lists = board.getLists().stream().map(ListResponseDto::new).toList();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        if (board.getAttachFiles().size() > 0) {
            this.imgId = board.getAttachFiles().get(0).getId();
        }else {
            this.imgId = null;
        }
    }
}
