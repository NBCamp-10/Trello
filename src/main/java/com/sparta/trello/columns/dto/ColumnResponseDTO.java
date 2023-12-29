package com.sparta.trello.columns.dto;

import com.sparta.trello.card.DTO.CardResponseDTO;
import com.sparta.trello.columns.entity.Columns;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ColumnResponseDTO {
    private Long columnId;
    private String columnName;
    private Long columnIndex;
    private List<CardResponseDTO> cards;

    public ColumnResponseDTO(Columns columns) {
        this.columnId = columns.getColumnId();
        this.columnName = columns.getColumnName();
        this.columnIndex = columns.getColumnIndex();
        this.cards = columns.getCardList().stream().map(CardResponseDTO::new).sorted(Comparator.comparingLong(CardResponseDTO::getCardIndex)).collect(Collectors.toList());
    }
}
