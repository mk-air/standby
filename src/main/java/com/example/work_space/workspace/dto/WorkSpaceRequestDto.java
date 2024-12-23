package com.example.work_space.workspace.dto;

import com.example.work_space.workspace.entity.WorkSpace;
import lombok.Getter;

@Getter
public class WorkSpaceRequestDto {

    private Long memberId; // 워크스페이스 생성자의 ID
    private String name; // 워크스페이스 이름
    private String description; // 워크스페이스 설명

    public WorkSpaceRequestDto(Long memberId, String name, String description) {
        this.memberId = memberId;
        this.name = name;
        this.description = description;
    }

    // WorkSpace 엔티티 변환 메서드
    public WorkSpace toEntity() {
        return WorkSpace.builder()
                .name(name)
                .description(description)
                .build();
    }
}
