package com.idealstudy.mvp.error;

import com.idealstudy.mvp.enums.ResultCase;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private final ResultCase resultCase;

    public GlobalException(ResultCase resultCase) {
        super(resultCase.getMsg());
        this.resultCase = resultCase;
    }
}
