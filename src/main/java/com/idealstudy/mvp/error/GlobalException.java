package com.idealstudy.mvp.error;

import com.idealstudy.mvp.enums.HttpResponse;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private final HttpResponse httpResponse;

    public GlobalException(HttpResponse httpResponse) {
        super(httpResponse.getMsg());
        this.httpResponse = httpResponse;
    }
}
