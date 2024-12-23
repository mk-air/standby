package com.example.work_space.filter;

import com.example.work_space.auth.entity.Authentication;
import com.example.work_space.constants.GlobalConstants;
import com.example.work_space.member.type.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class RoleFilter implements CommonFilter{

    private final Role role;

    public RoleFilter(Role role) {
        this.role = role;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = findHttpSession(servletRequest);

        Authentication authentication = (Authentication) session.getAttribute(GlobalConstants.USER_AUTH);

        Role clientRole = authentication.getRole();
        if (clientRole != this.role) {
            throw new IllegalArgumentException("사용자 권한이 없습니다.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
