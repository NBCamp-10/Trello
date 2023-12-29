package com.sparta.trello.card.DTO;

import com.sparta.trello.card.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CardResponseDTO {
    private Long cardId;
    private Long cardIndex;
    private String title;
    private String text;
    private String color;
    private String worker;
    private LocalDateTime deadline;
    private LocalDateTime createAt;
    private LocalDateTime modified;

    // 생성자 추가
    public CardResponseDTO(Card card) {
        this.cardId = card.getId();
        this.cardIndex = card.getCardIndex();
        this.title = card.getTitle();
        this.text = card.getText();
        this.color = card.getColor();
        this.worker = card.getWorker();
        this.deadline = card.getDeadline();
        this.createAt = card.getCreatedAt();
        this.modified = card.getModifiedAt();
    }
}
