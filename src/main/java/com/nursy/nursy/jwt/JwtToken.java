package com.nursy.nursy.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}
