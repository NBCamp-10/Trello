package com.sparta.trello.card.repository;

import com.sparta.trello.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByUserIdAndBoardIdAndColumn_ColumnIdAndId(Long userId, Long boardId, Long columnsId, Long cardId);
}
