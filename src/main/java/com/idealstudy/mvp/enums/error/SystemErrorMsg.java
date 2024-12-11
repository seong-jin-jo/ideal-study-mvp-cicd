package com.idealstudy.mvp.enums.error;

public enum SystemErrorMsg {

    IO_EXCEPTION("시스템 내부 입출력 오류");

    private final String msg;

    SystemErrorMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
