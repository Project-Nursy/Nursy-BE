package com.nursy.nursy.global.enums;

public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
