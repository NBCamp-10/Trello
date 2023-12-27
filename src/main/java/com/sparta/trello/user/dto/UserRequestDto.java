package com.sparta.trello.user.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {
    private String username;
    private String password;

    //권한 체크 여부-> 체크하면 ture
    private boolean admin=false;

    //권한 키
    private String adminToken="";
}
