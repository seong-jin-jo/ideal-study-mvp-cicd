package com.idealstudy.mvp.enums.member;

public enum Role {

    ROLE_GUEST,
    ROLE_STUDENT,
    ROLE_TEACHER,
    ROLE_PARENTS,
    ROLE_ADMIN;

    @Override
    public String toString() {
        return this.name();
    }

    public static Role stringToRole(String text) {
        try{
            return Role.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No constant with text " + text.toUpperCase() + " found");
        } catch (NullPointerException e) {
            throw new NullPointerException("EnumClass or name is null");
        }
    }
}
