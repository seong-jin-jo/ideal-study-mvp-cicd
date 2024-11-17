package com.idealstudy.mvp.infrastructure.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailTokenDto {

    private String email;
    private String token;
}
