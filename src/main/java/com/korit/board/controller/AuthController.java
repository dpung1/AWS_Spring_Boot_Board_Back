package com.korit.board.controller;

import com.korit.board.aop.annotation.ArgsAop;
import com.korit.board.aop.annotation.ReturnAop;
import com.korit.board.aop.annotation.TimeAop;
import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.dto.SigninReqDto;
import com.korit.board.dto.SignupReqDto;
import com.korit.board.service.AccountService;
import com.korit.board.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;


    @ArgsAop
    @ValidAop
    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@Valid  @RequestBody SignupReqDto signupReqDto, BindingResult bindingResult) {

        return ResponseEntity.ok().body(authService.signup(signupReqDto));
    }

    @ArgsAop
    @PostMapping("/auth/signin")
    public ResponseEntity<?> signin(@RequestBody SigninReqDto signinReqDto) {
        return ResponseEntity.ok().body(authService.signin(signinReqDto));
    }

    @GetMapping("/auth/token/authentication")
    public ResponseEntity<?> authenticate(@RequestHeader(value = "Authorization") String token) {

        return ResponseEntity.ok(true);
    }

    @GetMapping("/auth/mail")
    public ResponseEntity<?> authenticateMail(String token) {
        return ResponseEntity.ok(accountService.anthenticateMail(token) ? "인증이 완료되었습니다." : "인증 실패");
    }
}
