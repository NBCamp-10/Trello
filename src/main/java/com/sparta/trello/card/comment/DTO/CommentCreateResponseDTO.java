package com.sparta.trello.card.comment.DTO;

import com.sparta.trello.card.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateResponseDTO {
    private String text;

    @Builder
    private CommentCreateResponseDTO(Comment comment){
        this.text=comment.getText();
    }
}
