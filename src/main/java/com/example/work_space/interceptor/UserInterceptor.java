
package com.example.work_space.interceptor;

import com.example.work_space.auth.model.Authentication;
import com.example.work_space.constants.GlobalConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("로그인이 필요합니다.");
            return false;
        }

        // 이 부분에서 ClassCastException 발생 가능
        Authentication auth = (Authentication) session.getAttribute(GlobalConstants.USER_AUTH);
        if (auth == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("사용자 인증 정보가 없습니다.");
            return false;
        }

        return true;
    }
}
