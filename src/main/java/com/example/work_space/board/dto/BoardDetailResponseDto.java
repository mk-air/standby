package com.example.work_space.board.dto;

import com.example.work_space.board.entity.Board;
import com.example.work_space.list.dto.ListResponseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardDetailResponseDto {

    private Long workspaceId; // 워크스페이스 ID
    private Long id;

    @NotBlank
    private String title; // 보드 이름
    private String color; // 보드 배경색
    private Long imgId; // 보드 이미지 ID
    private List<ListResponseDto> lists;
    private String info; // 보드 설명

    private LocalDateTime updatedAt;

    public BoardDetailResponseDto(Board board) {
        this.workspaceId = board.getWorkSpace().getId();
        this.id = board.getId();
        this.title = board.getTitle();
        this.color = board.getColor();
        this.info = board.getInfo();
        this.updatedAt = board.getCreatedAt();
        this.lists = board.getLists().stream().map(ListResponseDto::new).toList();
        if (board.getAttachFiles().size() > 0) {
            this.imgId = board.getAttachFiles().get(0).getId();
        }else {
            this.imgId = null;
        }
    }
}
