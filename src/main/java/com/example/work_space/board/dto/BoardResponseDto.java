package com.example.work_space.board.dto;

import com.example.work_space.board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private Long workspaceId; // 워크스페이스 ID
    private Long id;
    private String title; // 보드 이름
    private String color; // 보드 배경색
    private Long imgId; // 보드 이미지 ID
    private String info; // 보드 설명
    private LocalDateTime createdAt;

    public BoardResponseDto(Board board) {
        this.workspaceId = board.getWorkSpace().getId();
        this.id = board.getId();
        this.title = board.getTitle();
        this.color = board.getColor();
        if (board.getAttachFiles().size() > 0) {
            this.imgId = board.getAttachFiles().get(0).getId();
        }else {
            this.imgId = null;
        }
        this.info = board.getInfo();
        this.createdAt = board.getCreatedAt();
    }

}
