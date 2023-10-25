package com.korit.board.service;

import com.korit.board.dto.BoardCategoryRespDto;
import com.korit.board.dto.WriteBoardReqDto;
import com.korit.board.entity.Board;
import com.korit.board.entity.BoardCategory;
import com.korit.board.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<BoardCategoryRespDto> getBoardCategoriesAll() {
        List<BoardCategoryRespDto> boardCategoryRespDtos = new ArrayList<>();
        boardMapper.getBoardCategories().forEach(category -> {
            boardCategoryRespDtos.add(category.toCategoryDto());
        });

        return boardCategoryRespDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean writeBoardContent(WriteBoardReqDto writeBoardReqDto) {

        BoardCategory boardCategory = null;

        if(writeBoardReqDto.getCategoryId() == 0) {
            boardCategory = BoardCategory.builder()
                    .boardCategoryName(writeBoardReqDto.getCategoryName())
                    .build();
            boardMapper.saveCategory(boardCategory);
            writeBoardReqDto.setCategoryId(boardCategory.getBoardCategoryId());
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Board board = writeBoardReqDto.toBoardEntity(email);
        return boardMapper.saveBoard(board) > 0;
    }
}
