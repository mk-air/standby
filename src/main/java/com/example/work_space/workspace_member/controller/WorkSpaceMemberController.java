package com.example.work_space.workspace_member.controller;

import com.example.work_space.constants.CommonResponse;
import com.example.work_space.workspace_member.dto.InviteMemberRequestDto;
import com.example.work_space.workspace_member.dto.WorkSpaceMemberRoleDto;
import com.example.work_space.workspace_member.dto.WorkSpaceMemberDto;
import com.example.work_space.workspace_member.service.WorkSpaceMemberService;
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
    public ResponseEntity<CommonResponse<Void>> inviteMember
            (@PathVariable Long workspaceId, @RequestParam Long inviterId,
             @RequestBody InviteMemberRequestDto inviteMemberRequestDto) {
        workSpaceMemberService.inviteMember(workspaceId, inviterId, inviteMemberRequestDto);
        return ResponseEntity.ok(new CommonResponse<>("초대 완료"));
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<CommonResponse<Page<WorkSpaceMemberDto>>> getWorkSpaceMembers
            (@PathVariable Long workspaceId,
             @RequestParam (defaultValue = "0") int page,
             @RequestParam (defaultValue = "10") int size) {
        Page<WorkSpaceMemberDto> members = workSpaceMemberService.getWorkSpaceMembers(workspaceId, page, size);
        return ResponseEntity.ok(new CommonResponse<>("멤버 조회", members));
    }

    @PatchMapping("/{workspaceId}/role")
    public ResponseEntity<CommonResponse<Void>> updateMemberRole(
            @PathVariable Long workspaceId,
            @RequestBody WorkSpaceMemberRoleDto workSpaceMemberRoleDto) {
        workSpaceMemberService.changeMemberRole(workspaceId, workSpaceMemberRoleDto);
        return ResponseEntity.ok(new CommonResponse<>("멤버 역할 변경 완료"));
    }
}
