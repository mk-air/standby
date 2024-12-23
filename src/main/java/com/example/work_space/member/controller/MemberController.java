package com.example.work_space.member.controller;

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
    public ResponseEntity<MemberResponseDto> registerMember
            (@RequestBody MemberRequestDto requestDto) {
        MemberResponseDto member = memberService.registerMember(requestDto);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember
            (@PathVariable Long memberId) {
        MemberResponseDto member = memberService.getMember(memberId);
        return ResponseEntity.ok(member);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember
            (@PathVariable Long memberId, @RequestBody MemberUpdateRequestDto requestDto) {
        MemberResponseDto member = memberService.updateMember(memberId, requestDto);
        return ResponseEntity.ok(member);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> deleteMember
            (@PathVariable Long memberId,
            @RequestParam String password) {
        memberService.deleteMember(memberId, password);
        return ResponseEntity.ok("회원 삭제 완료");
    }
}
