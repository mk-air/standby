package com.example.work_space.list.dto;

import lombok.Getter;

@Getter
public class ListUpdateRequestDto {

    private Long boardId;
    private String title;
    private Long seq;

    public ListUpdateRequestDto(Long boardId, String title, Long seq) {
        this.boardId = boardId;
        this.title = title;
        this.seq = seq;
    }
}
