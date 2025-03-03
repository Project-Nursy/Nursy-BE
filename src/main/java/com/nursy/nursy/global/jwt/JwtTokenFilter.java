package com.nursy.nursy.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // 특정 경로는 필터 제외 (예: 로그인, 회원가입)
        if (path.equals("/api/auth/login") || path.equals("/api/auth/join")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);
        if (token != null) {
            if (jwtTokenUtil.isExpired(token)) {
                String refreshToken = jwtTokenUtil.getRefreshTokenFromCookie(request);

                if (refreshToken != null && !jwtTokenUtil.isExpired(refreshToken)) {
                    String userId = jwtTokenUtil.getUserId(refreshToken);
                    String role = jwtTokenUtil.getUserRole(refreshToken);
                    String userName = jwtTokenUtil.getUserName(refreshToken);
                    //UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

                    String newAccessToken = jwtTokenUtil.createAccessToken(userId,userName,role);
                    setJsonResponseWithNewToken(response, newAccessToken);

                } else {
                    setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                    return;
                }
            } else {
                String userId = jwtTokenUtil.getUserId(token);
                String userRole = jwtTokenUtil.getUserRole(token);
                String userName = jwtTokenUtil.getUserName(token);

                UserDetails userDetails = new CustomUserDetails(userId,userName);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, List.of(new SimpleGrantedAuthority(userRole)));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
    private void setErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 에러 객체를 JSON으로 변환
        String jsonResponse = String.format("{\"status\":%d, \"error\":\"%s\"}", status, message);
        response.getWriter().write(jsonResponse);
    }
    private void setJsonResponseWithNewToken(HttpServletResponse response, String newAccessToken) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 응답 객체
        String jsonResponse = String.format("{\"status\":408, \"message\":\"New access token issued\", \"accessToken\":\"Bearer %s\"}", newAccessToken);
        response.getWriter().write(jsonResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
