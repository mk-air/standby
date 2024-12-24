package com.example.work_space.list.dto;

import lombok.Getter;

@Getter
public class ListUpdateRequestDto {

    private Long boardId;
    private Long id;
    private String title;
    private Long seq;

    public ListUpdateRequestDto(Long boardId, Long id, String title, Long seq) {
        this.boardId = boardId;
        this.id = id;
        this.title = title;
        this.seq = seq;
    }
}
