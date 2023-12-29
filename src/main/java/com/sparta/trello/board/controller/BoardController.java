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
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<CommonResponseDTO> createBoard(@RequestBody BoardRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            boardService.createBoard(requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDTO("보드 생성 완료", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping("") //사용자가 초대된 보드 조회
    public ResponseEntity<List<UserBoardResponseDto>> getUserIncludeBoard(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<UserBoardResponseDto> responseDto = boardService.getUserIncludeBoard(userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);

    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<CommonResponseDTO> updateBoard(@PathVariable Long boardId,
                                                         @RequestBody BoardRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            boardService.updateBoard(userDetails.getUser(), boardId, requestDto);
            return ResponseEntity.ok().body(new CommonResponseDTO("보드 수정 완료", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<CommonResponseDTO> deleteBoard(@PathVariable Long boardId,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            boardService.deleteBoard(userDetails.getUser(), boardId);
            return ResponseEntity.ok().body(new CommonResponseDTO("보드 삭제 완료", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/{boardId}/invite/{invitedUserId}")
    public ResponseEntity<CommonResponseDTO> inviteUserToBoard(@PathVariable Long boardId,
                                                               @PathVariable Long invitedUserId,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            boardService.inviteUserToBoard(userDetails.getUser(), boardId, invitedUserId);
            return ResponseEntity.ok().body(new CommonResponseDTO("초대 완료", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/{boardId}/role/{userId}")
    public ResponseEntity<CommonResponseDTO> changeUserRoleInBoard(@PathVariable Long boardId,
                                                                   @PathVariable Long userId,
                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {

        try {
            boardService.changeUserRoleInBoard(userDetails.getUser(), boardId, userId);
            return ResponseEntity.ok().body(new CommonResponseDTO("권한변경 완료", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


}
