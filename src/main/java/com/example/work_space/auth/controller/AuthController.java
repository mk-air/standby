package com.example.work_space.auth.controller;

import com.example.work_space.auth.dto.AuthRequestDto;
import com.example.work_space.auth.dto.AuthResponseDto;
import com.example.work_space.auth.service.AuthService;
import com.example.work_space.constants.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<CommonResponse<String>> login
            (@RequestBody AuthRequestDto requestDto,
             HttpServletRequest request) {
        HttpSession session = request.getSession();
        AuthResponseDto login = authService.login(requestDto, session);
        return ResponseEntity.ok(new CommonResponse<>("로그인 성공", login.getId().toString()));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<String>> logout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.ok(new CommonResponse<>("로그아웃 성공"));
    }
}
