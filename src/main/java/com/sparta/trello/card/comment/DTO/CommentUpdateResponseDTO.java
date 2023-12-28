package com.sparta.trello.card.comment.DTO;

import com.sparta.trello.card.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponseDTO {
    private String text;
    private LocalDateTime createAt;

    @Builder
    private CommentUpdateResponseDTO(Comment comment){
        this.createAt=comment.getCreatedAt();
        this.text=comment.getText();
    }
}
