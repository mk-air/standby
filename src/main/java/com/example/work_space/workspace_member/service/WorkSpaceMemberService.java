package com.example.work_space.workspace_member.service;

import com.example.work_space.workspace_member.dto.InviteMemberRequestDto;
import com.example.work_space.workspace_member.dto.MemberRoleDto;
import com.example.work_space.workspace_member.dto.WorkSpaceMemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkSpaceMemberService {
    Page<WorkSpaceMemberDto> getWorkSpaceMembers(Long workspaceId, int page, int size);
    void inviteMember(Long workspaceId, Long inviterId, InviteMemberRequestDto inviteMemberRequestDto);
    void changeMemberRole(Long workspaceId, MemberRoleDto memberRoleDto);
}
