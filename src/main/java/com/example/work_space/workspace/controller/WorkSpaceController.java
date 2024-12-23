package com.example.work_space.workspace.controller;

import com.example.work_space.constants.CommonResponse;
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
    public ResponseEntity<CommonResponse<WorkSpaceResponseDto>> createWorkSpace
            (
             @RequestBody WorkSpaceRequestDto requestDto) {
        WorkSpaceResponseDto workSpace = workSpaceService.createWorkSpace(requestDto);
        return ResponseEntity.ok(new CommonResponse<>("워크 스페이스 생성", workSpace));
    }

    @PatchMapping("/{workSpaceId}")
    public ResponseEntity<CommonResponse<WorkSpaceResponseDto>> updateWorkSpace
            (@PathVariable Long workSpaceId,
             @RequestBody WorkSpaceRequestDto requestDto) {
        WorkSpaceResponseDto workSpace = workSpaceService.updateWorkSpace(workSpaceId, requestDto);
        return ResponseEntity.ok(new CommonResponse<>("워크스페이스 수정", workSpace));
    }

    @DeleteMapping("/{workSpaceId}")
    public ResponseEntity<CommonResponse<String>> deleteWorkSpace
            (@PathVariable Long workSpaceId) {
        workSpaceService.deleteWorkSpace(workSpaceId);
        return ResponseEntity.ok(new CommonResponse<>("워크스페이스 삭제 완료"));
    }
}
