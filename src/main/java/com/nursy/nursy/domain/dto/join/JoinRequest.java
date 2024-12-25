package com.nursy.nursy.domain.dto.join;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class JoinRequest {
    private String name;
    private String userIdentifier;
    private String password;
    private String sex;
    private String phoneNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime birth;
    private String email;

}
