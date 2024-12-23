package com.example.work_space.workspace.service;

import com.example.work_space.workspace.dto.WorkSpaceRequestDto;
import com.example.work_space.workspace.dto.WorkSpaceResponseDto;

public interface WorkSpaceService {

    WorkSpaceResponseDto createWorkSpace(WorkSpaceRequestDto requestDto);
    WorkSpaceResponseDto updateWorkSpace(Long workSpaceId, WorkSpaceRequestDto requestDto);
    void deleteWorkSpace(Long workSpaceId);
}
