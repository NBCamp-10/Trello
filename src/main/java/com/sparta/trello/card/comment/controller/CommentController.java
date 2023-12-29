package com.sparta.trello.card.comment.controller;

import com.sparta.trello.card.comment.DTO.CommentCreateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentCreateResponseDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateResponseDTO;
import com.sparta.trello.card.comment.service.CommentService;
import com.sparta.trello.common.dto.CommonResponseDTO;
import com.sparta.trello.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{boardId}/{columnId}/{cardId}")
    public ResponseEntity<CommonResponseDTO> createComment (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable Long boardId,
                                                   @PathVariable Long columnId,
                                                   @PathVariable Long cardId,
                                                   @RequestBody CommentCreateRequestDTO commentCreateRequestDTO){
        try {
            commentService.createComment(commentCreateRequestDTO,userDetails.getUser(),boardId,columnId,cardId);
            return ResponseEntity.ok().body(new CommonResponseDTO("댓글생성 완료.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("/{boardId}/{columnId}/{cardId}/{commentId}")
    public ResponseEntity<CommonResponseDTO> updateComment (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable Long boardId,
                                                   @PathVariable Long columnId,
                                                   @PathVariable Long cardId,
                                                   @PathVariable Long commentId,
                                                   @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO){
        try {
            commentService.updateComment(commentUpdateRequestDTO, userDetails.getUser(), boardId, columnId, cardId, commentId);
            return ResponseEntity.ok().body(new CommonResponseDTO("댓글수정 완료.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{boardId}/{columnId}/{cardId}/{commentId}")
    public ResponseEntity<CommonResponseDTO> deleteComment (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @PathVariable Long boardId,
                                 @PathVariable Long columnId,
                                 @PathVariable Long cardId,
                                 @PathVariable Long commentId){

        try {
            commentService.deleteComment(userDetails.getUser(), boardId, columnId, cardId, commentId);
            return ResponseEntity.ok().body(new CommonResponseDTO("댓글 삭제 완료.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
