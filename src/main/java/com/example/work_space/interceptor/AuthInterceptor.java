package com.example.work_space.interceptor;

import com.example.work_space.auth.entity.Authentication;
import com.example.work_space.constants.GlobalConstants;
import com.example.work_space.member.type.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null) {
            System.out.println("세션 없음");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("로그인이 필요합니다.");
            return false;
        }

        Object userAuth = session.getAttribute(GlobalConstants.USER_AUTH);
        if (userAuth == null) {
            System.out.println("USER_AUTH 속성이 세션에 없음");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("로그인이 필요합니다.");
            return false;
        }

        System.out.println("세션 ID: " + session.getId() + ", USER_AUTH: " + userAuth);
        return true;
    }
}
