package com.sparta.trello.columns.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColumnRequestDTO {
    private String columnName;
    private Long columnIndex;
}