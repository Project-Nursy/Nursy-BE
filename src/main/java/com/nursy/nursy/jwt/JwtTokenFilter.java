package com.nursy.nursy.jwt;

import com.nursy.nursy.domain.Role;
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

        String token = getTokenFromRequest(request);
        if (token != null) {
            if (jwtTokenUtil.isExpired(token)) {
                String refreshToken = jwtTokenUtil.getRefreshTokenFromCookie(request);

                if (refreshToken != null && !jwtTokenUtil.isExpired(refreshToken)) {
                    String userId = jwtTokenUtil.getUserId(refreshToken);
                    String role = jwtTokenUtil.getUserRole(refreshToken);
                    //UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

                    String newAccessToken = jwtTokenUtil.createAccessToken(userId,role);
                    setJsonResponseWithNewToken(response, newAccessToken);

//                    response.setHeader("Authorization", "Bearer " + newAccessToken);
//                    response.getWriter().write("Authorization"+" "+ "Bearer " + newAccessToken);

                } else {
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    response.getWriter().write("JWT token and refresh token are expired.");
                    setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                    return;
                }
            } else {
                String userId = jwtTokenUtil.getUserId(token);
                String userRole = jwtTokenUtil.getUserRole(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

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
