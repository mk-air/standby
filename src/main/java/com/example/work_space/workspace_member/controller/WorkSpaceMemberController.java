package com.example.work_space.workspace_member.controller;

import com.example.work_space.workspace_member.dto.InviteMemberRequestDto;
import com.example.work_space.workspace_member.dto.MemberRoleDto;
import com.example.work_space.workspace_member.dto.WorkSpaceMemberDto;
import com.example.work_space.workspace_member.service.WorkSpaceMemberService;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspace-members")
public class WorkSpaceMemberController {

    private final WorkSpaceMemberService workSpaceMemberService;

    public WorkSpaceMemberController(WorkSpaceMemberService workSpaceMemberService) {
        this.workSpaceMemberService = workSpaceMemberService;
    }

    @PostMapping("/invite/{workspaceId}")
    public ResponseEntity<Void> inviteMember
            (@PathVariable Long workspaceId, @RequestParam Long inviterId,
             @RequestBody InviteMemberRequestDto inviteMemberRequestDto) {
        workSpaceMemberService.inviteMember(workspaceId, inviterId, inviteMemberRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<Page<WorkSpaceMemberDto>> getWorkSpaceMembers
            (@PathVariable Long workspaceId,
             @RequestParam (defaultValue = "0") int page,
             @RequestParam (defaultValue = "10") int size) {
        Page<WorkSpaceMemberDto> members = workSpaceMemberService.getWorkSpaceMembers(workspaceId, page, size);
        return ResponseEntity.ok(members);
    }

    @PatchMapping("/{workspaceId}/role")
    public ResponseEntity<Void> updateMemberRole(
            @PathVariable Long workspaceId,
            @RequestBody MemberRoleDto memberRoleDto) {
        workSpaceMemberService.changeMemberRole(workspaceId, memberRoleDto);
        return ResponseEntity.ok().build();
    }
}
