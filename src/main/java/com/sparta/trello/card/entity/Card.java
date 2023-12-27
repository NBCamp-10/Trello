package com.sparta.trello.card.entity;

import com.sparta.trello.card.DTO.CardCreateRequestDTO;
import com.sparta.trello.card.DTO.CardUpdateRequestDTO;
import com.sparta.trello.card.utils.TimeStamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name ="cards")
public class Card extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;
    private String color;
    private String worker;
    private LocalDateTime deadline;

    @Builder
    private Card (CardCreateRequestDTO cardCreateRequestDTO){
        this.title=cardCreateRequestDTO.getTitle();
        this.text=cardCreateRequestDTO.getText();
        this.color=cardCreateRequestDTO.getColor();
        this.worker=cardCreateRequestDTO.getWorker();
        this.deadline=cardCreateRequestDTO.getDeadline();
    }

    public void updateCard(CardUpdateRequestDTO cardUpdateRequestDTO){
        this.title=cardUpdateRequestDTO.getTitle();
        this.text=cardUpdateRequestDTO.getText();
        this.color=cardUpdateRequestDTO.getColor();
        this.worker=cardUpdateRequestDTO.getWorker();
        this.deadline=cardUpdateRequestDTO.getDeadline();
    }
}