package com.sparta.trello.card.DTO;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
public class CardCreateRequestDTO {
    private String title;
    private String text;
    private String color;
    private String worker;
    private LocalDateTime deadline;

}
