package com.sparta.trello.card.comment.entity;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.card.comment.DTO.CommentCreateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateRequestDTO;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.common.entity.Timestamped;
import com.sparta.trello.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private Columns column;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;


    private String text;

    @Builder
    private Comment(CommentCreateRequestDTO commentCreateRequestDTO,User user,Card card,Board board,Columns column){
        this.text=commentCreateRequestDTO.getText();
        this.user=user;
        this.board=board;
        this.column=column;
        this.card=card;
    }

    public void updateComment(CommentUpdateRequestDTO commentUpdateRequestDTO){
        this.text= commentUpdateRequestDTO.getText();
    }

}
