package com.nursy.nursy.controller;

import com.nursy.nursy.domain.dto.join.JoinRequest;
import com.nursy.nursy.domain.dto.login.AccessTokenResponseDto;
import com.nursy.nursy.domain.dto.login.LoginRequest;
import com.nursy.nursy.domain.entity.User;
import com.nursy.nursy.jwt.JwtToken;
import com.nursy.nursy.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
        if(jwtToken==null){
            //Map<String, String> response = new HashMap<>();
            //response.put("error", "Invalid username or password");
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디및 비밀번호를 체크해주세요");
        }
        AccessTokenResponseDto accessTokenResponseDto = new AccessTokenResponseDto();
        accessTokenResponseDto.setAccessToken(jwtToken.getAccessToken());

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", jwtToken.getRefreshToken())
                .httpOnly(true)
                .secure(true) // HTTPS 환경에서만 사용
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(accessTokenResponseDto);

        //return ResponseEntity.status(200).body(jwtToken);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // RefreshToken 쿠키 삭제
        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        // 응답에 삭제된 쿠키 설정
        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
    @GetMapping("/test")
    public ResponseEntity<?> test(Authentication authentication) {
        return ResponseEntity.ok(authentication);
    }

}
