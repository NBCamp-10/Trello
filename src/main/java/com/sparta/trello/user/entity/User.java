package com.sparta.trello.user.entity;

import com.sparta.trello.user.dto.IdChangeDto;
import com.sparta.trello.user.dto.PasswordChangeDto;
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

    @Column(nullable = false,unique = true)
    private String email;

    @Column
    private String introduce;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(UserRequestDto requestDto, String encodedPassword, UserRoleEnum role){
        this.username=requestDto.getUsername();
        this.password=encodedPassword;
        this.email= requestDto.getEmail();;
        this.introduce= requestDto.getIntroduce();
        this.role=role;
    }

    public void changeIdUser(IdChangeDto idChangeDto) {
        this.username= idChangeDto.getUsername();
    }


    public void changePasswordUser(String encodedPassword) {
        this.password=encodedPassword;
    }
}
