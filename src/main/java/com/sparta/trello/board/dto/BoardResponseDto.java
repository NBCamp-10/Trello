package com.sparta.trello.board.dto;


import com.sparta.trello.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String color;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public BoardResponseDto (Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.color = board.getColor();
        this.description = board.getDescription();
//        this.createdAt = board.getCreatedAt();
//        this.modifiedAt = board.getModifiedAt();
    }
}
