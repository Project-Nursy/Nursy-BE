package com.nursy.nursy.domain.dto.login;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String userIdentifier;
    private String password;
}
