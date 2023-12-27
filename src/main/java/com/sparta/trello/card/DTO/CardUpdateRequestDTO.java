package com.sparta.trello.card.DTO;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardUpdateRequestDTO {
    private String title;
    private String text;
    private String color;
    private String worker;
    private LocalDateTime deadline;
}
