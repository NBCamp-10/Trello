package com.sparta.trello.card.comment.controller;

import com.sparta.trello.card.comment.DTO.CommentCreateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentCreateResponseDTO;
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
    
}
