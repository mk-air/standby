package com.example.work_space.auth.dto;

import lombok.Getter;

@Getter
public class AuthRequestDto {
    private String email;
    private String password;

    public AuthRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
