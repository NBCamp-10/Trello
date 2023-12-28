package com.sparta.trello.columns.entity;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.dto.ColumnRequestDTO;
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
public class Columns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    @Column(nullable = false)
    private String columnName;

    @Column(nullable = false)
    private Long columnIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "columns", cascade = CascadeType.REMOVE)
//    private List<Card> commentList = new ArrayList<>();

    public Columns(Board board, ColumnRequestDTO columnRequestDTO, User user) {
        this.board = board;
        this.columnName = columnRequestDTO.getColumnName();
        this.columnIndex = columnRequestDTO.getColumnIndex();
        this.user = user;
    }

}
