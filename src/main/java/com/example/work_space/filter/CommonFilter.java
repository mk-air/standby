package com.example.work_space.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface CommonFilter extends Filter {

    default HttpSession findHttpSession(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);

        if (session == null) {
            System.out.println("세션 없음");
            throw new IllegalArgumentException("로그인 필요");
        }

        return session;
    }
}
