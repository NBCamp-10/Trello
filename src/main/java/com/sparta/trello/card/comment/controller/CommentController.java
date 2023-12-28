package com.sparta.trello.card.comment.controller;

import com.sparta.trello.card.comment.DTO.CommentCreateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentCreateResponseDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateResponseDTO;
import com.sparta.trello.card.comment.service.CommentService;
import com.sparta.trello.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{boardId}/{columnId}/{cardId}")
    public CommentCreateResponseDTO createComment (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable Long boardId,
                                                   @PathVariable Long columnId,
                                                   @PathVariable Long cardId,
                                                   @RequestBody CommentCreateRequestDTO commentCreateRequestDTO){
        return commentService.createComment(commentCreateRequestDTO,userDetails.getUser(),boardId,columnId,cardId);
    }

    @PatchMapping("/{boardId}/{columnId}/{cardId}/{commentId}")
    public CommentUpdateResponseDTO updateComment (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable Long boardId,
                                                   @PathVariable Long columnId,
                                                   @PathVariable Long cardId,
                                                   @PathVariable Long commentId,
                                                   @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO){
        return commentService.updateComment(commentUpdateRequestDTO, userDetails.getUser(), boardId, columnId, cardId, commentId);
    }

    @DeleteMapping("/{boardId}/{columnId}/{cardId}/{commentId}")
    public String deleteComment (@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @PathVariable Long boardId,
                                 @PathVariable Long columnId,
                                 @PathVariable Long cardId,
                                 @PathVariable Long commentId){
       commentService.deleteComment(userDetails.getUser(), boardId, columnId, cardId, commentId);
       return "삭제완료";
    }

}
