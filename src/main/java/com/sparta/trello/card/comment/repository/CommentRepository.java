package com.sparta.trello.card.comment.repository;

import com.sparta.trello.card.comment.entity.Comment;
import com.sparta.trello.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByUserIdAndBoardIdAndColumn_ColumnIdAndCardIdAndId(Long userId, Long boardId, Long columnsId, Long cardId, Long commentId);
}

