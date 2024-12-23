package com.example.work_space.member.entity;

import com.example.work_space.member.type.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean deleted = false;

    @Builder
    public Member(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    protected Member() {
    }

    public Member update(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        return this;
    }

    public void softDelete() {
        this.deleted = true;
    }
}
