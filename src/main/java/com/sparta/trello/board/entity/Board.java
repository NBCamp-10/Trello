package com.sparta.trello.board.entity;

import com.sparta.trello.board.dto.BoardRequestDto;
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
@Table(name = "board")
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<UserBoard> userBoardList = new ArrayList<>();

    public Board(BoardRequestDto requestDto, User user) {
        this.user = user;
        this.color = requestDto.getColor();
        this.description = requestDto.getDescription();
        this.title = requestDto.getTitle();
    }

    public void updateBoard(BoardRequestDto requestDto) {
        if (requestDto.getTitle() != null) {
            this.title = requestDto.getTitle();
        }
        if (requestDto.getColor() != null) {
            this.color = requestDto.getColor();
        }
        if (requestDto.getDescription() != null) {
            this.description = requestDto.getDescription();
        }
    }

}
