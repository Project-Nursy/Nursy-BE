package com.nursy.nursy.domain.user.dto.login;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String userIdentifier;
    private String password;
}
