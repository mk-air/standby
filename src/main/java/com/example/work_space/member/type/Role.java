package com.example.work_space.member.type;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("admin"),
    USER("user");

    private String role;

    Role(String role) {
        this.role = role;
    }

    private Role validateRole(String role) {
        if (role.equals("admin")) {
            return ADMIN;
        } else if (role.equals("user")) {
            return USER;
        } else {
            return null;
        }
    }
}
