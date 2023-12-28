package com.sparta.trello.card.entity;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.card.DTO.CardCreateRequestDTO;
import com.sparta.trello.card.DTO.CardUpdateRequestDTO;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.common.entity.Timestamped;
import com.sparta.trello.user.entity.User;
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
public class Card extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String text;
    private String color;
    private String worker;
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private Columns column;

    @Builder
    private Card (CardCreateRequestDTO cardCreateRequestDTO,User user,Board board,Columns column){
        this.title=cardCreateRequestDTO.getTitle();
        this.text=cardCreateRequestDTO.getText();
        this.color=cardCreateRequestDTO.getColor();
        this.worker=cardCreateRequestDTO.getWorker();
        this.deadline=cardCreateRequestDTO.getDeadline();
        this.user=user;
        this.board=board;
        this.column=column;
    }

    public void updateCard(CardUpdateRequestDTO cardUpdateRequestDTO){
        this.title=cardUpdateRequestDTO.getTitle();
        this.text=cardUpdateRequestDTO.getText();
        this.color=cardUpdateRequestDTO.getColor();
        this.worker=cardUpdateRequestDTO.getWorker();
        this.deadline=cardUpdateRequestDTO.getDeadline();
    }
}
