package com.example.work_space.list.dto;

import com.example.work_space.list.entity.List;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ListRequestDto {

    private Long boardId;

    @NotBlank
    private String title;
    private Long seq;

    public ListRequestDto(Long boardId, String title, Long seq) {
        this.boardId = boardId;
        this.title = title;
        this.seq = seq;
    }

    public ListRequestDto() {
    }

    public ListRequestDto(ListRequestDto requestDto) {
        this.boardId = requestDto.getBoardId();
        this.title = requestDto.getTitle();
        this.seq = requestDto.getSeq();
    }

    // Lists 엔티티 반환 메서드
    public List toEntity() {
        return List.builder()
                .title(title)
                .seq(seq)
                .build();
    }
}
