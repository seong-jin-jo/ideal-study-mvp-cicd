package com.idealstudy.mvp.enums.member;

import java.util.HashMap;
import java.util.Map;

public enum Role {

    // It should not start with "ROLE_"
    GUEST("GUEST"),
    STUDENT("STUDENT"),
    TEACHER("TEACHER"),
    PARENTS("PARENTS"),
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

    @Override
    public String toString() {
        return role;
    }

    public static Role stringToRole(String text) {
        Role role = ENUM_STRING.get(text.toUpperCase());
        if (role == null) {
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
        return role;
    }
}
