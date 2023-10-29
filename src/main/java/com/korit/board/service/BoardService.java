package com.korit.board.service;

import com.korit.board.dto.*;
import com.korit.board.entity.Board;
import com.korit.board.entity.BoardCategory;
import com.korit.board.entity.User;
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

    public List<BoardListRespDto> getBoardList(String categoryName, int page, SearchBoardListReqDto searchBoardListReqDto) {
        // 리스트가 1~10이면 0, 11~20이면 1 (SQL 쿼리문 limit 사용에 필요한 index)
        int index = (page - 1) * 10;

        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("index", index);
        paramsMap.put("categoryName", categoryName);
        paramsMap.put("optionName", searchBoardListReqDto.getOptionName());
        paramsMap.put("searchValue", searchBoardListReqDto.getSearchValue());

        List<BoardListRespDto> boardListRespDtos = new ArrayList<>();
        boardMapper.getBoardList(paramsMap).forEach(board -> {
            boardListRespDtos.add(board.toBoardListDto());
        });
        return boardListRespDtos;
    }

    public int getBoardCount(String categoryName, SearchBoardListReqDto searchBoardListReqDto) {
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("categoryName", categoryName);
        paramsMap.put("optionName", searchBoardListReqDto.getOptionName());
        paramsMap.put("searchValue", searchBoardListReqDto.getSearchValue());

        return boardMapper.getBoardCount(paramsMap);
    }

// 게시판

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

    public GetBoardRespDto getBoard(int boardId) {
        return boardMapper.getBoardByBoardId(boardId).toBoardDto();
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean editBoard(UpdateBoardReqDto updateBoardReqDto) {
        return boardMapper.updateBoard(updateBoardReqDto.updateBoard()) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean removeBoard(int boardId) {
        return boardMapper.removeBoard(boardId) > 0;
    }



// 좋아요

    public Boolean getLikeState(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());

        return boardMapper.getLikeState(paramsMap) > 0;
    }

    public Boolean setLike(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());

        return boardMapper.insertLike(paramsMap) > 0;
    }

    public Boolean cancelLike(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());

        return boardMapper.deleteLike(paramsMap) > 0;
    }
}
