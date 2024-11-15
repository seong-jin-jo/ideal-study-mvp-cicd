package com.idealstudy.mvp.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCase {

    SAMPLE(HttpStatus.OK, "샘플 테스트");

    private final HttpStatus httpStatus;
    private final String msg;

    ResultCase(HttpStatus httpStatus, String msg){
        this.httpStatus = httpStatus;
        this.msg = msg;
    }
}
