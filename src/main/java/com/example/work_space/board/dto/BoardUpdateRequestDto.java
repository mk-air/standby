package com.example.work_space.board.dto;

import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {
    private Long workspaceId; // 워크스페이스 ID
    private Long memberId;
    private String title; // 보드 이름
    private String color; // 보드 배경색
    private String img; // 보드 이미지
    private String info; // 보드 설명

    private BoardUpdateRequestDto(String title, String color, String img, String info, Long workspaceId, Long memberId) {
        this.title = title;
        this.color = color;
        this.img = img;
        this.info = info;
        this.workspaceId = workspaceId;
        this.memberId = memberId;
    }
}