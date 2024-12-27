package com.example.work_space.list.dto;

import com.example.work_space.list.entity.List;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ListRequestDto {

    private Long boardId;

    @NotBlank
    private String title;

    public ListRequestDto(Long boardId, String title){
        this.boardId = boardId;
        this.title = title;
    }

    public ListRequestDto() {
    }

    public ListRequestDto(ListRequestDto requestDto) {
        this.boardId = requestDto.getBoardId();
        this.title = requestDto.getTitle();
    }

    // Lists 엔티티 반환 메서드
    public List toEntity() {
        return List.builder()
                .title(title)
                .build();
    }
}
