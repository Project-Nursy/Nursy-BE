package com.nursy.nursy.domain.entity;

import com.nursy.nursy.domain.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long cno;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // UUID 자동 생성
    private String id;

    private String name;
    private String userIdentifier;
    private String password;
    private String sex;
    private String phoneNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
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
    @PrePersist
    public void prePersist() {
        if (this.role == null) {
            this.role = Role.ROLE_USER;
        }
        this.createdAt = LocalDateTime.now();
    }

}