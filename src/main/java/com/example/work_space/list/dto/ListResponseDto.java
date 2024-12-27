package com.example.work_space.list.dto;

import com.example.work_space.list.entity.List;
import com.example.work_space.card.dto.CardResponseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ListResponseDto {

    private Long boardId; // 보드 ID
    private Long id;

    @NotBlank
    private String title; // 리스트 제목
    private Long seq; // 리스트 순서
    private java.util.List<CardResponseDto> cards;
    private LocalDateTime updatedAt;

    public ListResponseDto(Long boardId, Long id, String title, Long seq, LocalDateTime updatedAt) {
        this.boardId = boardId;
        this.id = id;
        this.title = title;
        this.seq = seq;
        this.updatedAt = updatedAt;
    }

    public ListResponseDto(List list) {
        this.boardId = list.getBoard().getId();
        this.id = list.getId();
        this.title = list.getTitle();
        this.seq = list.getSeq();
        this.cards = list.getCards().stream().map(CardResponseDto::new).toList();
        this.updatedAt = list.getUpdatedAt();
    }
}
