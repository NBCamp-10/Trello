package com.sparta.trello.columns.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false, unique = true)
    private Long columnIndex;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

//    @OneToMany(mappedBy = "columns", cascade = CascadeType.REMOVE)
//    private List<Card> commentList = new ArrayList<>();

    public Columns(Board board, ColumnRequestDTO columnRequestDTO) {
        this.board = board;
        this.columnName = columnRequestDTO.getColumnName();
        this.columnIndex = columnRequestDTO.getColumnIndex();
    }

}
