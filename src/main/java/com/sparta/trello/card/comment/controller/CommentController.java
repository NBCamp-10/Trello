package com.sparta.trello.card.comment.controller;

import com.sparta.trello.card.comment.DTO.CommentCreateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentCreateResponseDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateResponseDTO;
import com.sparta.trello.card.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{cardId}")
    public CommentCreateResponseDTO createComment (@PathVariable Long cardId,
                                                   @RequestBody CommentCreateRequestDTO commentCreateRequestDTO){
        return commentService.createComment(commentCreateRequestDTO, cardId);
    }

    @PatchMapping("/{cardId}/{commentId}")
    public CommentUpdateResponseDTO updateComment (@PathVariable Long cardId,
                                                   @PathVariable Long commentId,
                                                   @RequestBody CommentUpdateRequestDTO commentUpdateRequestDTO){
        return commentService.updateComment(commentUpdateRequestDTO, cardId, commentId);
    }

    @DeleteMapping("/{cardId}/{commentId}")
    public String deleteComment (@PathVariable Long cardId,
                               @PathVariable Long commentId){
       commentService.deleteComment( cardId, commentId);
       return "삭제완료";
    }

}
