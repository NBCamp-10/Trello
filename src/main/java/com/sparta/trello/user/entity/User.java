package com.sparta.trello.user.entity;

import com.sparta.trello.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(UserRequestDto requestDto, String encodedPassword, UserRoleEnum role){
        this.username=requestDto.getUsername();
        this.password=encodedPassword;
        this.role=role;
    }
}
