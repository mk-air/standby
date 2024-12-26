package com.example.work_space.list.dto;

import com.example.work_space.list.entity.List;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ListResponseDto {

    private Long boardId; // 보드 ID
    private Long id;
    private String title; // 리스트 제목
    private Long seq; // 리스트 순서
//    private List<CardResponseDto> cards;
    private LocalDateTime createdAt;

    public ListResponseDto(Long boardId, Long id, String title, Long seq, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.id = id;
        this.title = title;
        this.seq = seq;
        this.createdAt = createdAt;
    }

    public ListResponseDto(List list) {
        this.boardId = list.getBoard().getId();
        this.id = list.getId();
        this.title = list.getTitle();
        this.seq = list.getSeq();
        this.createdAt = list.getCreatedAt();
    }
}
