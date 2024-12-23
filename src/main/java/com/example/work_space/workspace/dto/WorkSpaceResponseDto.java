package com.example.work_space.workspace.dto;

import lombok.Getter;

@Getter
public class WorkSpaceResponseDto {

    private Long id;

    public WorkSpaceResponseDto(Long id) {
        this.id = id;
    }
}
