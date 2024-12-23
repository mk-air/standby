package com.example.work_space.member.dto;

import com.example.work_space.member.entity.Member;
import com.example.work_space.member.type.Role;
import lombok.Getter;

@Getter
public class MemberRequestDto {
    private String name;
    private String email;
    private String password;
    private Role role;

    public MemberRequestDto(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .role(role)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}
