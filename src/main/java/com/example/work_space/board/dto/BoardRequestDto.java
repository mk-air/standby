package com.example.work_space.board.dto;

import com.example.work_space.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardRequestDto {

    private Long workspaceId; // 워크스페이스 ID
    private String title; // 보드 이름
    private String color; // 보드 배경색
    private String img; // 보드 이미지
    private String info; // 보드 설명

    public BoardRequestDto(Long workspaceId, String title, String color, String img, String info) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.color = color;
        this.img = img;
        this.info = info;
    }

    public BoardRequestDto(BoardRequestDto requestDto) {
        this.workspaceId = requestDto.getWorkspaceId();
        this.title = requestDto.getTitle();
        this.color = requestDto.getColor();
        this.img = requestDto.getImg();
        this.info = requestDto.getInfo();
    }

    // Board 엔티티 반환 메서드
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .color(color)
                .img(img)
                .info(info)
                .build();
    }
}
