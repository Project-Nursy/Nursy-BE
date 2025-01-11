package com.nursy.nursy.controller;

import com.nursy.nursy.domain.dto.join.JoinRequest;
import com.nursy.nursy.domain.dto.login.LoginRequest;
import com.nursy.nursy.domain.entity.User;
import com.nursy.nursy.jwt.JwtToken;
import com.nursy.nursy.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    /**
     * 회원가입,회원 탈퇴
     * 로그인,로그아웃
     * oauth로그인
     */
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<?> register(@RequestBody JoinRequest joinRequest) {

        User user = authService.signUp(joinRequest);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        JwtToken jwtToken = authService.login(loginRequest);

        return ResponseEntity.status(200).body(jwtToken);
    }
    @GetMapping("/test")
    public ResponseEntity<?> test(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }


}
