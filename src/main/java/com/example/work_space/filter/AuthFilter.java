package com.example.work_space.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

public class AuthFilter implements CommonFilter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        findHttpSession(servletRequest);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
