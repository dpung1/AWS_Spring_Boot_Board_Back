package com.korit.board.exception;

public class MismatchedPasswordException extends RuntimeException {

    public MismatchedPasswordException() {
        super("새 비밀번호가 서로 일치하지 않습니다.");
    }
}
