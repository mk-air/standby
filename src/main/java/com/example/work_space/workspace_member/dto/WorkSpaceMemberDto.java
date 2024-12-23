package com.example.work_space.workspace_member.dto;

import com.example.work_space.workspace_member.entity.WorkSpaceMember;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WorkSpaceMemberDto {
    private Long workSpaceId;
    private Long memberId;

    @Builder
    public WorkSpaceMemberDto(Long workSpaceId, Long memberId) {
        this.workSpaceId = workSpaceId;
        this.memberId = memberId;
    }

    public static WorkSpaceMemberDto toEntity(WorkSpaceMember workSpaceMember) {
        return WorkSpaceMemberDto.builder()
                .workSpaceId(workSpaceMember.getWorkSpace().getId())
                .memberId(workSpaceMember.getMember().getId())
                .build();
    }
}
