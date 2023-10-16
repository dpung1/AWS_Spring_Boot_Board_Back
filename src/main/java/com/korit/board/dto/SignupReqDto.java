package com.korit.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class SignupReqDto {

    @Email
    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotBlank
    String name;

    @NotBlank
    String nickname;
}
