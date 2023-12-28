package com.sparta.trello.board.controller;

import com.sparta.trello.common.dto.CommonResponseDTO;
import com.sparta.trello.board.dto.BoardRequestDto;
import com.sparta.trello.board.dto.BoardResponseDto;
import com.sparta.trello.board.dto.UserBoardResponseDto;
import com.sparta.trello.board.service.BoardService;
import com.sparta.trello.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        BoardResponseDto createBoard = boardService.createBoard(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(createBoard);
    }

    @GetMapping("") //사용자가 초대된 보드 조회
    public List<UserBoardResponseDto> getUserIncludeBoard(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return boardService.getUserIncludeBoard(userDetails.getUser());
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardId,
                                                        @RequestBody BoardRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto updateBoard = boardService.updateBoard(userDetails.getUser(), boardId, requestDto);
        return ResponseEntity.ok().body(updateBoard);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<CommonResponseDTO> deleteBoard(@PathVariable Long boardId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(userDetails.getUser(), boardId);
        return ResponseEntity.ok().body(new CommonResponseDTO("삭제 되었습니다.", HttpStatus.OK.value()));
    }

    @PostMapping("/{boardId}/invite/{invitedUserId}")
    public ResponseEntity<CommonResponseDTO> inviteUserToBoard(@PathVariable Long boardId,
                                                               @PathVariable Long invitedUserId,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {

        boardService.inviteUserToBoard(userDetails.getUser(), boardId, invitedUserId);
        return ResponseEntity.ok().body(new CommonResponseDTO("초대되었습니다.", HttpStatus.OK.value()));
    }

    @PatchMapping("/{boardId}/role/{userId}")
    public ResponseEntity<CommonResponseDTO> changeUserRoleInBoard(@PathVariable Long boardId,
                                                                   @PathVariable Long userId,
                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {

        boardService.changeUserRoleInBoard(userDetails.getUser(), boardId, userId);
        return ResponseEntity.ok().body(new CommonResponseDTO("권한변경 완료", HttpStatus.OK.value()));
    }


}
