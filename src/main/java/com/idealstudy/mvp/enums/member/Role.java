package com.idealstudy.mvp.enums.member;

public enum Role {

    // It should not start with "ROLE_"
    STUDENT("STUDENT"),
    TEACHER("TEACHER"),
    ADMIN("ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    /*
    public static Role stringToEnum(String role) {
        for(Role val : Role.values())
            if(val.getRoleStr().equals(role))
                return val;
        
        throw new IllegalArgumentException(MemberError.ROLL_MISS_MATCH.getMsg());
    }

     */
}
