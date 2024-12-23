package com.example.work_space.auth.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {
    private Long id;

    public AuthResponseDto(Long id) {
        this.id = id;
    }
}
