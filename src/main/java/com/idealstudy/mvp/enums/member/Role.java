package com.idealstudy.mvp.enums.member;

import java.util.HashMap;
import java.util.Map;

public enum Role {

    // It should not start with "ROLE_"
    STUDENT("STUDENT"),
    TEACHER("TEACHER"),
    ADMIN("ADMIN");

    private static final Map<String, Role> ENUM_STRING = new HashMap<>();

    static {
        for (Role role : values()) {
            ENUM_STRING.put(role.role, role);
        }
    }

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String toString() {
        return role;
    }

    public static Role fromString(String text) {
        Role role = ENUM_STRING.get(text.toUpperCase());
        if (role == null) {
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
        return role;
    }
}
