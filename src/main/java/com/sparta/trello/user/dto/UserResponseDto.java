package com.sparta.trello.user.dto;

import com.sparta.trello.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto extends CommonResponseDto{
    private String username;
    private String email;
    private String introduce;

    public UserResponseDto(User user){
        this.username=user.getUsername();
        this.email=user.getEmail();
        this.introduce=user.getIntroduce();
    }
}
