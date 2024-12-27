package com.example.work_space.member.dto;

import lombok.Getter;

@Getter
public class MemberDeleteRequestDto {
    private String password;

    public MemberDeleteRequestDto(String password) {
        this.password = password;
    }
}
