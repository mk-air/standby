package com.example.work_space.workspace_member.dto;

import com.example.work_space.workspace.type.WorkSpaceRole;
import lombok.Getter;

@Getter
public class WorkSpaceMemberRoleDto {

    private Long memberId; // 멤버 ID
    private WorkSpaceRole role; // 멤버 역할

    public WorkSpaceMemberRoleDto(Long memberId, WorkSpaceRole role) {
        this.memberId = memberId;
        this.role = role;
    }


}
