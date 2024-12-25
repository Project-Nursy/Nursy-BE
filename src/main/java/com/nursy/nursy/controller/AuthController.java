package com.nursy.nursy.controller;

import com.nursy.nursy.domain.dto.join.JoinRequest;
import com.nursy.nursy.domain.entity.User;
import com.nursy.nursy.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<?> register(JoinRequest joinRequest) {
        User user = authService.signUp(joinRequest);

        return ResponseEntity.ok(user);
    }
}
