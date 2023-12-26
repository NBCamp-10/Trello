package com.sparta.trello.card.comment.DTO;

import com.sparta.trello.card.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentUpdateResponseDTO {
    private String text;


    @Builder
    private CommentUpdateResponseDTO(Comment comment){
        this.text=comment.getText();
    }
}
