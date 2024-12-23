package com.example.work_space.auth.controller;

import com.example.work_space.auth.dto.AuthRequestDto;
import com.example.work_space.auth.dto.AuthResponseDto;
import com.example.work_space.auth.service.AuthService;
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
    public ResponseEntity<String> login
            (@RequestBody AuthRequestDto requestDto,
             HttpServletRequest request) {
        HttpSession session = request.getSession();
        AuthResponseDto login = authService.login(requestDto, session);
        return ResponseEntity.ok("로그인 완료");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.ok("로그아웃 완료");
    }
}
