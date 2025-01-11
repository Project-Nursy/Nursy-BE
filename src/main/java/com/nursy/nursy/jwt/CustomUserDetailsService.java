package com.nursy.nursy.jwt;

import com.nursy.nursy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //return new CustomUserDetails(userRepository.findByUserIdentifier(username).get());
        return new CustomUserDetails(username);//userIdentifier //이후 user pk로 변경예정 uuid?
    }
}
