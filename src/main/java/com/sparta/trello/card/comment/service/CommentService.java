package com.sparta.trello.card.comment.service;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.service.BoardService;
import com.sparta.trello.card.comment.DTO.CommentCreateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentCreateResponseDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateRequestDTO;
import com.sparta.trello.card.comment.DTO.CommentUpdateResponseDTO;
import com.sparta.trello.card.comment.entity.Comment;
import com.sparta.trello.card.comment.repository.CommentRepository;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.service.CardService;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.user.entity.User;
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
    private final BoardService boardService;
    public CommentCreateResponseDTO createComment(CommentCreateRequestDTO commentCreateRequestDTO, User user, Long boardId, Long columnId, Long cardId) {

        Card card=cardService.getCard(user.getId(),boardId,columnId,cardId);

        if (card == null) {
            throw new IllegalArgumentException("해당 카드가 존재하지 않습니다.");
        }

        Board board= boardService.findByBoard(boardId);

        if (board == null) {
            throw new IllegalArgumentException("해당 보드가 존재하지 않습니다.");
        }

        Columns column=cardService.getColumn(columnId);

        if (column == null) {
            throw new IllegalArgumentException("해당 컬럼이 존재하지 않습니다.");
        }
        if (commentCreateRequestDTO == null || commentCreateRequestDTO.getText().isEmpty()) {
            throw new IllegalArgumentException("text를 입력 해주세요.");
        }

        Comment comment=Comment.builder()
                               .user(user)
                               .board(board)
                               .column(column)
                               .card(card)
                               .commentCreateRequestDTO(commentCreateRequestDTO)
                               .build();

            commentRepository.save(comment);

        CommentCreateResponseDTO commentCreateResponseDTO=CommentCreateResponseDTO.builder().comment(comment).build();

            return commentCreateResponseDTO;

    }

    public CommentUpdateResponseDTO updateComment(CommentUpdateRequestDTO commentUpdateRequestDTO, User user, Long boardId, Long columnId, Long cardId, Long commentId) {
        // 1. CommentRepository를 사용하여 코멘트를 찾음
        Comment comment = getComment(user.getId(),boardId,columnId,cardId,commentId);

        // 2. Comment 엔티티 업데이트
        comment.updateComment(commentUpdateRequestDTO);

        // 3. Comment 엔티티를 사용하여 CommentUpdateResponseDTO 생성
        CommentUpdateResponseDTO commentUpdateResponseDTO = CommentUpdateResponseDTO.builder().comment(comment).build();

        return commentUpdateResponseDTO;
    }

    public void deleteComment(User user, Long boardId, Long columnId, Long cardId, Long commentId) {
        // 1. CommentRepository를 사용하여 코멘트를 찾음
        Comment comment = getComment(user.getId(), boardId, columnId, cardId, commentId);
        commentRepository.delete(comment);
    }

    public Comment getComment(Long userId, Long boardId, Long columnId, Long cardId, Long commentId){
        return  commentRepository.findByUserIdAndBoardIdAndColumn_ColumnIdAndCardIdAndId(userId, boardId, columnId, cardId, commentId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 카드의 코멘트를 찾을 수 없습니다: "));
    }
}
