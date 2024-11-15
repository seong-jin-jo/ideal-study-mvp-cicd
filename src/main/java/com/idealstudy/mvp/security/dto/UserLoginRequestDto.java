package com.idealstudy.mvp.security.dto;

public record UserLoginRequestDto(
        String username,
        String password
) {
}
