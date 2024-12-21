package com.nursy.nursy.domain.entity;

import com.nursy.nursy.domain.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    private String name;
    private String userIdentifier;
    private String password;
    private String sex;
    private String phoneNum;
    private LocalDateTime birth;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "varchar(255) default 'USER'")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Auth> auths;

    @OneToMany(mappedBy = "user")
    private List<Ward> wards;

    // Getters and Setters
}