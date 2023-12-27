package com.sparta.trello.board.entity;

import com.sparta.trello.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Table(name = "board")
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "description", nullable = false)
    private String description;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;


    public Board(BoardRequestDto requestDto){
        this.color = requestDto.getColor();
        this.description = requestDto.getDescription();
        this.title = requestDto.getTitle();
    }

    public void updateBoard(BoardRequestDto requestDto){
        if (requestDto.getTitle() != null){
            this.title = requestDto.getTitle();
        }
        if (requestDto.getColor() != null){
            this.color = requestDto.getColor();
        }
        if (requestDto.getDescription() != null){
            this.description = requestDto.getDescription();
        }
    }

}
