package com.sparta.trello.board.dto;

import com.sparta.trello.board.entity.UserBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserBoardResponseDto {
    private Long userId;
    private Long boardId;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public UserBoardResponseDto(UserBoard userBoard) {
        this.userId = userBoard.getUser().getId();
        this.boardId = userBoard.getBoard().getId();
        this.title = userBoard.getBoard().getTitle();
        this.description = userBoard.getBoard().getDescription();
        this.createAt = userBoard.getBoard().getCreatedAt();
        this.modifiedAt = userBoard.getBoard().getModifiedAt();
    }
}