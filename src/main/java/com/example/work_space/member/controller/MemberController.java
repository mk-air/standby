package com.example.work_space.member.controller;

import com.example.work_space.constants.CommonResponse;
import com.example.work_space.member.dto.MemberDeleteRequestDto;
import com.example.work_space.member.dto.MemberRequestDto;
import com.example.work_space.member.dto.MemberResponseDto;
import com.example.work_space.member.dto.MemberUpdateRequestDto;
import com.example.work_space.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<MemberResponseDto>> registerMember
            (@RequestBody MemberRequestDto requestDto) {
        MemberResponseDto member = memberService.registerMember(requestDto);
        return ResponseEntity.ok(new CommonResponse<>("회원 생성", member));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<CommonResponse<MemberResponseDto>> getMember
            (@PathVariable Long memberId) {
        MemberResponseDto member = memberService.getMember(memberId);
        return ResponseEntity.ok(new CommonResponse<>("회원 조회",member));
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<CommonResponse<MemberResponseDto>> updateMember
            (@PathVariable Long memberId, @RequestBody MemberUpdateRequestDto requestDto) {
        MemberResponseDto member = memberService.updateMember(memberId, requestDto);
        return ResponseEntity.ok(new CommonResponse<>("회원 수정", member));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<CommonResponse<String>> deleteMember
            (@PathVariable Long memberId,
             @RequestBody MemberDeleteRequestDto requestDto) {
        memberService.deleteMember(memberId, requestDto);
        return ResponseEntity.ok(new CommonResponse<>("회원 삭제 완료"));
    }
}
