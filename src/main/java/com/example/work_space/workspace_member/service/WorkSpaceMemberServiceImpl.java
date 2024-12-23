package com.example.work_space.workspace_member.service;

import com.example.work_space.member.entity.Member;
import com.example.work_space.member.repository.MemberRepository;
import com.example.work_space.workspace.entity.WorkSpace;
import com.example.work_space.workspace.repository.WorkSpaceRepository;
import com.example.work_space.workspace.type.WorkSpaceRole;
import com.example.work_space.workspace_member.dto.InviteMemberRequestDto;
import com.example.work_space.workspace_member.dto.MemberRoleDto;
import com.example.work_space.workspace_member.dto.WorkSpaceMemberDto;
import com.example.work_space.workspace_member.entity.WorkSpaceMember;
import com.example.work_space.workspace_member.repository.WorkSpaceMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkSpaceMemberServiceImpl implements WorkSpaceMemberService {

    private final WorkSpaceMemberRepository workSpaceMemberRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final MemberRepository memberRepository;

    public WorkSpaceMemberServiceImpl(WorkSpaceMemberRepository workSpaceMemberRepository, WorkSpaceRepository workSpaceRepository, MemberRepository memberRepository) {
        this.workSpaceMemberRepository = workSpaceMemberRepository;
        this.workSpaceRepository = workSpaceRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public Page<WorkSpaceMemberDto> getWorkSpaceMembers(Long workspaceId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<WorkSpaceMember> members = workSpaceMemberRepository.findByWorkSpaceId(workspaceId, pageable);

        // 엔티티를 DTO로 변환
        return members.map(WorkSpaceMemberDto::toEntity);
    }

    @Transactional
    @Override
    public void inviteMember(Long workspaceId, Long inviterId, InviteMemberRequestDto inviteMemberRequestDto) {
        // 워크스페이스 가져오기
        WorkSpace workSpace = workSpaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 워크스페이스가 존재하지 않습니다."));

        // 초대하는 사용자가 해당 워크스페이스의 멤버인지 확인
        WorkSpaceMember inviter = workSpaceMemberRepository.findByWorkSpaceIdAndMemberId(workspaceId, inviterId)
                .orElseThrow(() -> new IllegalArgumentException("초대하는 사용자가 워크스페이스의 멤버가 아닙니다."));

        // 초대하는 사용자의 권한 확인
        if (inviter.getRole() != WorkSpaceRole.ADMIN && inviter.getRole() != WorkSpaceRole.MEMBER) {
            throw new IllegalArgumentException("초대 권한이 없습니다.");
        }

        // 초대할 멤버 가져오기
        Member memberToInvite = memberRepository.findById(inviteMemberRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        // 초대하려는 멤버가 이미 워크스페이스에 있는지 확인
        boolean alreadyMember = workSpaceMemberRepository.existsByWorkSpaceIdAndMemberId(workspaceId, memberToInvite.getId());
        if (alreadyMember) {
            throw new IllegalArgumentException("이미 멤버입니다.");
        }

        // 워크스페이스 멤버 생성
        WorkSpaceMember workSpaceMember = WorkSpaceMember.builder()
                .workSpace(workSpace)
                .member(memberToInvite)
                .role(WorkSpaceRole.MEMBER) // 초대된 멤버의 기본 역할 지정 (필요 시 변경 가능)
                .build();

        // 저장
        workSpaceMemberRepository.save(workSpaceMember);
    }

    @Transactional
    public void changeMemberRole(Long workspaceId, MemberRoleDto memberRoleDto) {
        // 멤버 조회
        WorkSpaceMember workSpaceMember = workSpaceMemberRepository.findByWorkSpaceIdAndMemberId(workspaceId, memberRoleDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("워크스페이스 멤버를 찾을 수 없습니다."));

        // 역할 변경
        workSpaceMember.changeRole(memberRoleDto.getRole());
    }
}
