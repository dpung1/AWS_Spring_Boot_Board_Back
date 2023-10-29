package com.korit.board.controller;


import com.korit.board.aop.annotation.ArgsAop;
import com.korit.board.aop.annotation.TimeAop;
import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.dto.*;
import com.korit.board.service.BoardService;
import com.korit.board.service.PrincipalUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final PrincipalUserDetailService principalUserDetailService;

    @GetMapping("/board/categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(boardService.getBoardCategoriesAll());
    }

    @ValidAop
    @PostMapping("/board/content")
    public ResponseEntity<?> writeBoard(@Valid @RequestBody WriteBoardReqDto writeBoardReqDto, BindingResult bindingResult) {
        return ResponseEntity.ok(boardService.writeBoardContent(writeBoardReqDto));
    }

    @GetMapping("/boards/{categoryName}/{page}")
    public ResponseEntity<?> getBoardList(@PathVariable String categoryName, @PathVariable int page, SearchBoardListReqDto searchBoardListReqDto) {
        return ResponseEntity.ok(boardService.getBoardList(categoryName, page, searchBoardListReqDto));
    }

    @GetMapping("/boards/{categoryName}/count")
    public ResponseEntity<?> getBoardCount(@PathVariable String categoryName, SearchBoardListReqDto searchBoardListReqDto) {
        return ResponseEntity.ok(boardService.getBoardCount(categoryName, searchBoardListReqDto));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @ArgsAop
    @PutMapping("/board/{boardId}")
    public ResponseEntity<?> editBoard(@RequestBody UpdateBoardReqDto updateBoardReq) {
        return ResponseEntity.ok(boardService.editBoard(updateBoardReq));
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<?> removeBoard(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.removeBoard(boardId));
    }

    @GetMapping("/board/like/{boardId}")
    public ResponseEntity<?> getLikeState(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.getLikeState(boardId));
    }

    @PostMapping("/board/like/{boardId}")
    public ResponseEntity<?> setLike(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.setLike(boardId));
    }

    @DeleteMapping("/board/like/{boardId}")
    public ResponseEntity<?> cancelLike(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.cancelLike(boardId));
    }

    @ArgsAop
    @PostMapping("/board/write/point")
    public ResponseEntity<?> pointDisCount(@RequestBody PointDisCountReqDto pointDisCountReqDto) {
        System.out.println(pointDisCountReqDto);
        System.out.println(pointDisCountReqDto.toUserPointEntity());
        return ResponseEntity.ok(principalUserDetailService.pointDisCount(pointDisCountReqDto));
    }
}
