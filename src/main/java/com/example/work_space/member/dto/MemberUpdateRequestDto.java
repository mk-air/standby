package com.example.work_space.member.dto;

import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {
    private String name;
    private String email;
    private String password;

    public MemberUpdateRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
