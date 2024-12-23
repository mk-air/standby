package com.example.work_space.auth.service;

import com.example.work_space.auth.dto.AuthRequestDto;
import com.example.work_space.auth.dto.AuthResponseDto;
import com.example.work_space.auth.entity.Authentication;
import com.example.work_space.config.PasswordEncoder;
import com.example.work_space.constants.GlobalConstants;
import com.example.work_space.member.entity.Member;
import com.example.work_space.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDto login(AuthRequestDto requestDto, HttpSession session) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email=" + requestDto.getEmail()));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 세션에 Authentication 객체 저장
        Authentication auth = new Authentication(member.getId(), member.getRole());
        session.setAttribute(GlobalConstants.USER_AUTH, auth);
        session.setAttribute("role", member.getRole());


        return new AuthResponseDto(member.getId());
    }

    public void logout(HttpSession session) {
        // 로그아웃 로직
        if (session != null) {
            session.invalidate();
        }
    }
}
