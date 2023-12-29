package com.sparta.trello.columns.entity;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.entity.UserBoard;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.dto.ColumnRequestDTO;
import com.sparta.trello.common.entity.Timestamped;
import com.sparta.trello.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="columns")
public class Columns extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    @Column(nullable = false)
    private String columnName;

    @Column
    private Long columnIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "column", cascade = CascadeType.REMOVE)
    private List<Card> cardList = new ArrayList<>();


    public Columns(Board board, String columnName, Long columnIndex, User user) {
        this.board = board;
        this.columnName = columnName;
        this.columnIndex = columnIndex;
        this.user = user;
    }

}
