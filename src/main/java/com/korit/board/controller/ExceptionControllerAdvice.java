package com.korit.board.controller;

import com.korit.board.aop.annotation.TimeAop;
import com.korit.board.exception.ValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {


    @ExceptionHandler(ValidException.class)

    @TimeAop
    public ResponseEntity<?> validException(ValidException validException) {
//        System.out.println("예외처리 !!");
        return ResponseEntity.badRequest().body(validException.getErrorMap());
    }
}
