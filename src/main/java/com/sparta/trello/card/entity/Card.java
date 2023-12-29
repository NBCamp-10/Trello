package com.sparta.trello.card.entity;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.common.entity.Timestamped;
import com.sparta.trello.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name ="cards")
public class Card extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String color;

    private String worker;

    private LocalDateTime deadline;

    private Long cardIndex;

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
    private Card (String title, String text, String color, String worker, LocalDateTime deadline,User user,Board board,Columns column, Long cardIndex){
        this.title=title;
        this.text=text;
        this.color=color;
        this.worker=worker;
        this.deadline=deadline;
        this.user=user;
        this.board=board;
        this.column=column;
        this.cardIndex=cardIndex;
    }

    public void moveInColumn(Long cardIndex){
        this.cardIndex=cardIndex;
    }

    public void moveCard(Columns column, Long cardIndex){
        this.column=column;
        this.cardIndex=cardIndex;
    }

}
