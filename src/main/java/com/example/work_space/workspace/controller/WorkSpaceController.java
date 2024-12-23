package com.example.work_space.workspace.controller;

import com.example.work_space.workspace.dto.WorkSpaceRequestDto;
import com.example.work_space.workspace.dto.WorkSpaceResponseDto;
import com.example.work_space.workspace.service.WorkSpaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspaces")
public class WorkSpaceController {

    private final WorkSpaceService workSpaceService;

    public WorkSpaceController(WorkSpaceService workSpaceService) {
        this.workSpaceService = workSpaceService;
    }

    @PostMapping
    public ResponseEntity<WorkSpaceResponseDto> createWorkSpace
            (
             @RequestBody WorkSpaceRequestDto requestDto) {
        WorkSpaceResponseDto workSpace = workSpaceService.createWorkSpace(requestDto);
        return ResponseEntity.ok(workSpace);
    }

    @PatchMapping("/{workSpaceId}")
    public ResponseEntity<WorkSpaceResponseDto> updateWorkSpace
            (@PathVariable Long workSpaceId,
             @RequestBody WorkSpaceRequestDto requestDto) {
        WorkSpaceResponseDto workSpace = workSpaceService.updateWorkSpace(workSpaceId, requestDto);
        return ResponseEntity.ok(workSpace);
    }

    @DeleteMapping("/{workSpaceId}")
    public ResponseEntity<String> deleteWorkSpace
            (@PathVariable Long workSpaceId) {
        workSpaceService.deleteWorkSpace(workSpaceId);
        return ResponseEntity.ok("워크스페이스 삭제 완료");
    }
}
