package com.sparta.trello.card.DTO;

import lombok.Getter;

@Getter
public class CardUpdateRequestDTO {
    private String title;
    private String text;
    private String color;
    private String worker;
    private String deadline;
}
