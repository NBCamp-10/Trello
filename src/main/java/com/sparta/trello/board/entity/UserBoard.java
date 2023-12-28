package com.sparta.trello.board.entity;

import com.sparta.trello.user.entity.User;
import com.sparta.trello.user.entity.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user-board")
public class UserBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userBoard_id")
    private Long id;

    @Column
    private String username;

    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public UserBoard(UserRoleEnum role, Board board, User user) {
        this.username = user.getUsername();
        this.role = role;
        this.board = board;
        this.user = user;
    }
}