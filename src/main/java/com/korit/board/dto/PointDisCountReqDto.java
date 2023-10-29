package com.korit.board.dto;

import com.korit.board.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PointDisCountReqDto {
    private int userPoint;
    private String email;

    public User toUserPointEntity() {
        return User.builder()
                .userPoint(userPoint)
                .email(email)
                .build();
    }
}
