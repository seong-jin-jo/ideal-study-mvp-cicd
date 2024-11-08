package com.idealstudy.mvp.enums.member;

public enum Role {
    STUDENT("학생"),
    TEACHER("강사"),
    ADMIN("관리자");

    private final String role;

    Role(String role){
        this.role = role;
    }

    public String getRoleStr() {
        return role;
    }

    public static Role stringToEnum(String role) {
        for(Role val : Role.values())
            if(val.getRoleStr().equals(role))
                return val;
        
        throw new IllegalArgumentException("알 수 없는 역할");
    }
}
