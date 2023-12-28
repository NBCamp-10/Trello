package com.sparta.trello.user.dto;

import lombok.Getter;

@Getter
public class PasswordChangeDto {
    private String beforePassword;
    private String afterPassword;
}
