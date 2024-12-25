package com.nursy.nursy.service;

import com.nursy.nursy.domain.dto.join.JoinRequest;
import com.nursy.nursy.domain.entity.User;
import com.nursy.nursy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
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
}
