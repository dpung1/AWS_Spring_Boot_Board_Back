package com.korit.board.dto;

import com.korit.board.entity.Board;
import lombok.Data;

@Data
public class UpdateBoardReqDto {

    private int boardId;
    private int categoryId;
    private String title;
    private String content;

    public Board updateBoard() {
        return Board.builder()
                .boardId(boardId)
                .boardCategoryId(categoryId)
                .boardTitle(title)
                .boardContent(content)
                .build();
    }

}
