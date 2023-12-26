package com.sparta.trello.card.comment.service;

import com.sparta.trello.card.comment.DTO.CommentCreateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentCreateResponseDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateResponseDTO;
import com.sparta.trello.card.comment.entity.Comment;
import com.sparta.trello.card.comment.repository.CommentRepository;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardService cardService;
    public CommentCreateResponseDTO createComment(CommentCreateRequestDTO commentCreateRequestDTO,Long cardId) {

        Card card=cardService.getCard(cardId);

        Comment comment=Comment.builder()
                               .card(card)
                               .commentCreateRequestDTO(commentCreateRequestDTO)
                               .build();

            commentRepository.save(comment);

        CommentCreateResponseDTO commentCreateResponseDTO=CommentCreateResponseDTO.builder().comment(comment).build();

            return commentCreateResponseDTO;

    }

    public CommentUpdateResponseDTO updateComment(CommentUpdateRequestDTO commentUpdateRequestDTO, Long cardId, Long commentId) {
        // 1. CommentRepository를 사용하여 코멘트를 찾음
        Comment comment = commentRepository.findByCardIdAndId(cardId, commentId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 카드의 코멘트를 찾을 수 없습니다: " + cardId + ", " + commentId));

        // 2. Comment 엔티티 업데이트
        comment.updateComment(commentUpdateRequestDTO);

        // 3. Comment 엔티티를 사용하여 CommentUpdateResponseDTO 생성
        CommentUpdateResponseDTO commentUpdateResponseDTO = CommentUpdateResponseDTO.builder().comment(comment).build();

        return commentUpdateResponseDTO;
    }

    public void deleteComment(Long cardId, Long commentId) {
        // 1. CommentRepository를 사용하여 코멘트를 찾음
        Comment comment = commentRepository.findByCardIdAndId(cardId, commentId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 카드의 코멘트를 찾을 수 없습니다: " + cardId + ", " + commentId));

        commentRepository.delete(comment);
    }
}
