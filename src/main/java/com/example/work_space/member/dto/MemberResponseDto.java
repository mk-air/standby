package com.example.work_space.member.dto;

import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;

    public MemberResponseDto(Long id) {
        this.id = id;
    }
}
