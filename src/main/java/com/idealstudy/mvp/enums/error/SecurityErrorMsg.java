package com.idealstudy.mvp.enums.error;


public enum SecurityErrorMsg {

    ROLE_EXCEPTION("권한이 없습니다."),
    PRIVATE_EXCEPTION("타인의 비밀글에는 접근할 수 없습니다.");


    private final String msg;

    SecurityErrorMsg(String s) {

        this.msg = s;
    }

    @Override
    public String toString() {
        return msg;
    }
}
