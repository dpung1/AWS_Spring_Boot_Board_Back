package com.korit.board.dto;

import com.korit.board.entity.Board;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class WriteBoardReqDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Min(0) // int, integer = NotBlank 불가
    private int categoryId;

    @NotBlank
    private String categoryName;

    public Board toBoardEntity(String email) {
        return Board.builder()
                .boardTitle(title)
                .boardCategoryId(categoryId)
                .boardContent(content)
                .email(email)
                .build();
    }
}
