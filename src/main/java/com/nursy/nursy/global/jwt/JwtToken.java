package com.nursy.nursy.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}
