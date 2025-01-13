package com.nursy.nursy.jwt;

import com.nursy.nursy.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret.key}")
    private String secret;
    @Value("${jwt.expire.access}")
    private Long accessTokenExpireMs;
    @Value("${jwt.expire.refresh}")
    private Long refreshTokenExpireMs;
    //토큰발급
    public String createToken(String userIdentifier,String role,String type ,Long expireTimeMs){
        Claims claims = Jwts.claims();
        //claims.put("userId", user.getCno().toString());
        claims.put("userId", userIdentifier);
        claims.put("role",role);
        return Jwts.builder()
                .setHeaderParam("typ", type)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }
    public String createAccessToken(String userIdentifier,String role){
        return createToken(userIdentifier,role,"access",accessTokenExpireMs);
    }
    public String createRefreshToken(String userIdentifier,String role){
        return createToken(userIdentifier,role,"refresh",refreshTokenExpireMs);
    }

    public String getUserId(String token){
        return extractClaims(token).get("userId",String.class);
    }
    public String getUserRole(String token){
        return extractClaims(token).get("role",String.class);
    }

    // 토큰 만료 확인
    public boolean isExpired(String token) {
        try {
            Date expiredDate = extractClaims(token).getExpiration();
            return expiredDate.before(new Date());
        } catch (Exception e) {
            // 토큰 파싱 중 오류 발생 시 만료된 것으로 간주
            return true;
        }
    }

    // Claims 추출
    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token", e);
        }
    }
    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
