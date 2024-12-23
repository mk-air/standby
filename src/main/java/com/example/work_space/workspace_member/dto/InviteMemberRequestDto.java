package com.example.work_space.workspace_member.dto;

import com.example.work_space.member.type.Role;
import com.example.work_space.workspace.type.WorkSpaceRole;
import lombok.Getter;

@Getter
public class InviteMemberRequestDto {
    private Long memberId;
    private WorkSpaceRole role;

    public InviteMemberRequestDto(Long memberId, WorkSpaceRole role) {
        this.memberId = memberId;
        this.role = role;
    }
}
