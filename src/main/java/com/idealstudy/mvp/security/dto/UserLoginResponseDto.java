package com.idealstudy.mvp.security.dto;

import lombok.Getter;

public record UserLoginResponseDto(

        String username,
        String token
) {

    public static UserLoginResponseDto create(String username, String token) {
        return new UserLoginResponseDto(username, token);
    }
}
