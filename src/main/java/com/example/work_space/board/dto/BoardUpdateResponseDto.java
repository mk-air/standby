package com.example.work_space.board.dto;

import com.example.work_space.board.entity.Board;
import com.example.work_space.list.dto.ListResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardUpdateResponseDto {

    private final Long workspaceId; // 워크스페이스 ID
    private final Long id;
    private final String title; // 보드 이름
    private final String color; // 보드 배경색
    private final Long imgId; // 보드 이미지 ID
    private final String info; // 보드 설명
    private final List<ListResponseDto> lists;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public BoardUpdateResponseDto(Board board) {
        this.workspaceId = board.getWorkSpace().getId();
        this.id = board.getId();
        this.title = board.getTitle();
        this.color = board.getColor();
        this.info = board.getInfo();
        this.lists = board.getLists().stream().map(ListResponseDto::new).toList();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
        if (!board.getAttachFiles().isEmpty()) {
            this.imgId = board.getAttachFiles().get(0).getId();
        }else {
            this.imgId = null;
        }
    }
}
