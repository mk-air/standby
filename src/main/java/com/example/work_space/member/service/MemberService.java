package com.example.work_space.member.service;

import com.example.work_space.member.dto.MemberRequestDto;
import com.example.work_space.member.dto.MemberResponseDto;
import com.example.work_space.member.dto.MemberUpdateRequestDto;


public interface MemberService {
    MemberResponseDto registerMember(MemberRequestDto memberRequestDto);
    MemberResponseDto getMember(Long memberId);
    MemberResponseDto updateMember(Long memberId, MemberUpdateRequestDto memberRequestDto);
    void deleteMember(Long id, String password);
}
