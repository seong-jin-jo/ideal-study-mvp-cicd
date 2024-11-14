package com.idealstudy.mvp.enums.member;

public enum Role {
    STUDENT,
    TEACHER,
    ADMIN;

    /*
    public static Role stringToEnum(String role) {
        for(Role val : Role.values())
            if(val.getRoleStr().equals(role))
                return val;
        
        throw new IllegalArgumentException(MemberError.ROLL_MISS_MATCH.getMsg());
    }

     */
}
