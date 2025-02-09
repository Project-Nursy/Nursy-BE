package com.nursy.nursy.domain.user;

import com.nursy.nursy.domain.user.dto.join.JoinRequest;
import com.nursy.nursy.domain.user.dto.login.LoginRequest;
import com.nursy.nursy.global.jwt.JwtToken;
import com.nursy.nursy.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public User signUp(JoinRequest joinRequest) {
        User user = User.builder()
                .userIdentifier(joinRequest.getUserIdentifier())
                .password(passwordEncoder.encode(joinRequest.getPassword()))
                .name(joinRequest.getName())
                .birth(joinRequest.getBirth())
                .sex(joinRequest.getSex())
                .email(joinRequest.getEmail())
                .phoneNum(joinRequest.getPhoneNum())
                .build();
        userRepository.save(user);

        return user;
    }
    public JwtToken login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByUserIdentifier(loginRequest.getUserIdentifier());
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return null;
        }

        String accessToken = jwtTokenUtil.createAccessToken(user.getId(),user.getName(),user.getRole().toString());
        String refreshToken = jwtTokenUtil.createRefreshToken(user.getId(), user.getName(), user.getRole().toString());


        return new JwtToken(accessToken,refreshToken);
    }
}
