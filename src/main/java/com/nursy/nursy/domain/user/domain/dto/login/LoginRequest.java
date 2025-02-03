package com.nursy.nursy.domain.user.domain.dto.login;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String userIdentifier;
    private String password;
}
