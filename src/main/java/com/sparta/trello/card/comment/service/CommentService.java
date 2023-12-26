package com.sparta.trello.card.comment.service;

import com.sparta.trello.card.comment.DTO.CommentCreateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentCreateResponseDTO;
import com.sparta.trello.card.comment.entity.Comment;
import com.sparta.trello.card.comment.repository.CommentRepository;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
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
}
