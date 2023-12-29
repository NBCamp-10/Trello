package com.sparta.trello.columns.repository;

import com.sparta.trello.columns.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {
    Optional<Columns> findByColumnName(String columnName);
    Long countByBoardId(Long boardId);
    List<Columns> findByBoardId(Long boardId);
    List<Columns> findAllByOrderByColumnIndex();
}
