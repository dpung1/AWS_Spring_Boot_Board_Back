package com.korit.board.entity;

import com.korit.board.dto.BoardListRespDto;
import com.korit.board.dto.GetBoardRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
    private int boardId;
    private String boardTitle;

    private int boardCategoryId;
    private String boardCategoryName;

    private String boardContent;
    private String email;

    private String nickname;

    private LocalDateTime createDate;

    private int boardHitsCount;
    private int boardLikeCount;

    public BoardListRespDto toBoardListDto() {
        return BoardListRespDto.builder()
                .boardId(boardId)
                .title(boardTitle)
                .nickname(nickname)
                // DateTimeFormatter.ISO_DATE (년,월,일)
                .createDate(createDate.format(DateTimeFormatter.ISO_DATE))
                .hitsCount(boardHitsCount)
                .likeCount(boardLikeCount)
                .build();
    }

    public GetBoardRespDto toBoardDto() {
        return GetBoardRespDto.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardCategoryId(boardCategoryId)
                .boardCategoryName(boardCategoryName)
                .email(email)
                .nickname(nickname)
                // LONG = 일까지 , FULL = 요일
                .createDate(createDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)))
                .boardHitsCount(boardHitsCount)
                .boardLikeCount(boardLikeCount)
                .build();
    }
}
