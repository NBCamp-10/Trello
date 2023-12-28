package com.sparta.trello.board.controller;

import com.sparta.trello.board.dto.BoardRequestDto;
import com.sparta.trello.board.dto.BoardResponseDto;
import com.sparta.trello.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponseDto> addBoard(@RequestBody  BoardRequestDto boardRequestDto){
        BoardResponseDto responseDto = boardService.createBoard(boardRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getBoards(){
        List<BoardResponseDto> responseDto = boardService.getBoardList();
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{boardsId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardsId, @RequestBody BoardRequestDto requestDto){
        BoardResponseDto responseDto = boardService.updateBoard(boardsId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{boardsId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardsId){
        boardService.deleteBoard(boardsId);
        return ResponseEntity.noContent().build();
    }

}
