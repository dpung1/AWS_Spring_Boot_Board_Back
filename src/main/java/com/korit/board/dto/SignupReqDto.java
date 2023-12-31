package com.korit.board.dto;

import com.korit.board.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    private String oauth2Id;
    private String profileImg;
    private String provider;

    public User toUserEntity(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .oauth2Id(oauth2Id)
                .provider(provider)
                .profileUrl(profileImg)
                .build();
    }
}
