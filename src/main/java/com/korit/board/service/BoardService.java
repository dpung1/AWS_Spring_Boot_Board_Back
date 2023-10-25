package com.korit.board.service;

import com.korit.board.dto.BoardCategoryRespDto;
import com.korit.board.dto.BoardListRespDto;
import com.korit.board.dto.SearchBoardListReqDto;
import com.korit.board.dto.WriteBoardReqDto;
import com.korit.board.entity.Board;
import com.korit.board.entity.BoardCategory;
import com.korit.board.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public List<BoardListRespDto> getBoardList(String categoryName, int page, SearchBoardListReqDto searchBoardListReqDto) {
        // 리스트가 1~10이면 0, 11~20이면 1 (SQL 쿼리문 limit 사용에 필요한 index)
        int index = (page - 1) * 10;

        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("index", index);
        paramsMap.put("categoryName", categoryName);
        paramsMap.put("optionName", searchBoardListReqDto.getOptionName());
        paramsMap.put("searchValue", searchBoardListReqDto.getSearchValue());

        // DB에 보내주고 요게 리스트로 날라옴
        List<BoardListRespDto> boardListRespDtos = new ArrayList<>();
        boardMapper.getBoardList(paramsMap).forEach(board -> {
            boardListRespDtos.add(board.toBoardListDto());
        });
        return boardListRespDtos;
    }
}
