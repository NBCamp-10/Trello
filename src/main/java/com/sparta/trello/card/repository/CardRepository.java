package com.sparta.trello.card.repository;

import com.sparta.trello.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByUserIdAndBoardIdAndColumn_ColumnIdAndId(Long userId, Long boardId, Long columnsId, Long cardId);

    //특정 컬럼에 속하는 Card 엔터티의 개수를 조회합니다.
    Long countByColumn_ColumnId(Long columnId);
    List<Card> findByUserIdAndBoardIdAndColumn_ColumnIdOrderByCardIndex(Long userId, Long boardId, Long columnId);

    // boardId와 columnId에 해당하는 카드를 cardIndex순으로 정렬하여 가져오는 메서드
    List<Card> findByBoardIdAndColumn_ColumnIdOrderByCardIndex(Long boardId, Long columnId);
}
