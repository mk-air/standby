package com.example.work_space.auth.model;

import com.example.work_space.member.type.Role;
import lombok.Getter;

@Getter
public class Authentication {

    private final Long id;
    private final Role role;

    public Authentication(Long id, Role role) {
        this.id = id;
        this.role = role;
    }
}
