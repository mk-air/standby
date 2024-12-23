package com.example.work_space.workspace.service;

import com.example.work_space.member.entitiy.Member;
import com.example.work_space.member.repository.MemberRepository;
import com.example.work_space.workspace.dto.WorkSpaceRequestDto;
import com.example.work_space.workspace.dto.WorkSpaceResponseDto;
import com.example.work_space.workspace.entity.WorkSpace;
import com.example.work_space.workspace.repository.WorkSpaceRepository;
import com.example.work_space.workspace.type.WorkSpaceRole;
import com.example.work_space.workspace_member.entity.WorkSpaceMember;
import com.example.work_space.workspace_member.repository.WorkSpaceMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {


    private final WorkSpaceRepository workSpaceRepository;
    private final MemberRepository memberRepository;
    private final WorkSpaceMemberRepository workSpaceMemberRepository;

    public WorkSpaceServiceImpl(WorkSpaceRepository workSpaceRepository,
                                MemberRepository memberRepository,
                                WorkSpaceMemberRepository workSpaceMemberRepository) {
        this.workSpaceRepository = workSpaceRepository;
        this.memberRepository = memberRepository;
        this.workSpaceMemberRepository = workSpaceMemberRepository;
    }

    @Transactional
    @Override
    public WorkSpaceResponseDto createWorkSpace(WorkSpaceRequestDto requestDto) {

        // 사용자 확인
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        // 워크스페이스 생성
        WorkSpace workSpace = workSpaceRepository.save(requestDto.toEntity());

        // 워크스페이스 생성자를 ADMIN으로 설정
        WorkSpaceMember workSpaceMember = WorkSpaceMember.builder()
                .member(member)
                .workSpace(workSpace)
                .role(WorkSpaceRole.ADMIN)
                .build();
        workSpaceMemberRepository.save(workSpaceMember); // 수정된 부분

        // 응답 반환
        return new WorkSpaceResponseDto(workSpace.getId());
    }

    @Transactional
    @Override
    public WorkSpaceResponseDto updateWorkSpace(Long workSpaceId, WorkSpaceRequestDto requestDto) {
        // 워크스페이스 존재 확인
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 워크스페이스가 존재하지 않습니다. ID: " + workSpaceId));

        // 엔티티 업데이트
        workSpace.update(requestDto.toEntity());

        // 수정된 엔티티 저장 후 응답 DTO 반환
        WorkSpace updatedWorkSpace = workSpaceRepository.save(workSpace);
        return new WorkSpaceResponseDto(updatedWorkSpace.getId());
    }

    @Transactional
    @Override
    public void deleteWorkSpace(Long workSpaceId) {
        // 워크스페이스 존재 확인
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 워크스페이스가 존재하지 않습니다. ID: " + workSpaceId));

        // 워크스페이스 삭제
        workSpaceRepository.delete(workSpace);
    }
}
