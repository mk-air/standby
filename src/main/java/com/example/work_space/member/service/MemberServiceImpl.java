package com.example.work_space.member.service;

import com.example.work_space.config.PasswordEncoder;
import com.example.work_space.member.dto.MemberRequestDto;
import com.example.work_space.member.dto.MemberResponseDto;
import com.example.work_space.member.dto.MemberUpdateRequestDto;
import com.example.work_space.member.entity.Member;
import com.example.work_space.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public MemberResponseDto registerMember(MemberRequestDto memberRequestDto) {

        log.info("회원 중복 검사 : {}", memberRequestDto.getEmail());
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            log.error("이메일이 존재합니다.");
            throw new IllegalArgumentException("이메일이 존재합니다.");
        }

        log.info("비밀번호 암호화");
        String encodedPassword = passwordEncoder.encode(memberRequestDto.getPassword());

        log.info("회원 생성");
        return new MemberResponseDto(memberRepository.save(memberRequestDto.toEntity(encodedPassword)).getId());
    }

    @Transactional(readOnly = true)
    @Override
    public MemberResponseDto getMember(Long memberId) {
        log.info("회원 조회");
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        log.error("해당 회원이 존재하지 않습니다.");

        return new MemberResponseDto(member.getId());
    }

    @Transactional
    @Override
    public MemberResponseDto updateMember(Long memberId, MemberUpdateRequestDto memberRequestDto) {
        log.info("회원 수정");
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        log.error("해당 회원이 존재하지 않습니다.");

        String encodedPassword = passwordEncoder.encode(memberRequestDto.getPassword());
        Member updatedMember = member.update(memberRequestDto.getName(), memberRequestDto.getEmail(), encodedPassword);

        return new MemberResponseDto(memberRepository.save(updatedMember).getId());
    }


    @Transactional
    @Override
    public void deleteMember(Long id, String password) {
        // 삭제되지 않은 회원만 조회
        Member member = memberRepository.findActiveById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않거나 이미 삭제되었습니다."));


        String encodedPassword = passwordEncoder.encode(member.getPassword());

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, member.getPassword())) {
            log.error("비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 소프트 삭제 수행
        member.softDelete();
    }
}
