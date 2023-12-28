package com.sparta.trello.card.repository;

import com.sparta.trello.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByUserIdAndBoardIdAndColumn_ColumnIdAndId(Long userId, Long boardId, Long columnsId, Long cardId);

    //특정 컬럼에 속하는 Card 엔터티의 개수를 조회합니다.
    Long countByColumn_ColumnId(Long columnId);

    //컬럼 ID와 카드 인덱스를 사용하여 카드를 조회합니다.
    Optional<Card> findByColumn_ColumnIdAndCardIndex(Long columnId, Long cardIndex);
}
