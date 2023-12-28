package com.sparta.trello.card.DTO;

import com.sparta.trello.card.entity.Card;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardCreateResponseDTO {
    private String title;
    private String text;
    private String color;
    private String worker;
    private LocalDateTime deadline;
    private LocalDateTime createAt;
    @Builder
    private CardCreateResponseDTO(Card card){
        this.title=card.getTitle();
        this.text=card.getText();
        this.color=card.getColor();
        this.worker=card.getWorker();
        this.deadline=card.getDeadline();
        this.createAt=card.getCreatedAt();
    }
}
