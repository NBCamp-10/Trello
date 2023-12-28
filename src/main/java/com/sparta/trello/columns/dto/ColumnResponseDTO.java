package com.sparta.trello.columns.dto;

import com.sparta.trello.columns.entity.Columns;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnResponseDTO {
    private Long columnId;
    private String columnName;
    private Long columnIndex;

    public ColumnResponseDTO(Columns columns) {
        this.columnId = columns.getColumnId();
        this.columnName = columns.getColumnName();
        this.columnIndex = columns.getColumnIndex();
    }
}
