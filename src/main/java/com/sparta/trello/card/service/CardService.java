package com.sparta.trello.card.service;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.service.BoardService;
import com.sparta.trello.card.DTO.CardCreateRequestDTO;
import com.sparta.trello.card.DTO.CardCreateResponseDTO;
import com.sparta.trello.card.DTO.CardUpdateRequestDTO;
import com.sparta.trello.card.DTO.CardUpdateResponseDTO;
import com.sparta.trello.card.repository.CardRepository;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.columns.repository.ColumnsRepository;
import com.sparta.trello.columns.service.ColumnService;
import com.sparta.trello.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Transactional
public class CardService {

    private final CardRepository cardRepository;
    private final BoardService boardService;
    private final ColumnsRepository columnsRepository;
    public CardCreateResponseDTO createCard(CardCreateRequestDTO cardCreateRequestDTO, User user, Long boardId, Long columnId) {
            Board board = boardService.findByBoard(boardId);

            Columns column = getColumn(columnId);
            // 특정 컬럼에 속하는 카드의 개수를 조회하고 1을 더한후 새로 생성될 카드의 인덱스에 대입 합니다.
            Long cardIndex = getCardCountByColumn (columnId)+1L;

            Card card=Card.builder()
                          .cardIndex(cardIndex)
                          .board(board)
                          .column(column)
                          .user(user)
                          .cardCreateRequestDTO(cardCreateRequestDTO)
                          .build();

            cardRepository.save(card);

            CardCreateResponseDTO cardCreateResponseDTO=CardCreateResponseDTO.builder().card(card).build();

            return cardCreateResponseDTO;

    }

    public CardUpdateResponseDTO updateCard(CardUpdateRequestDTO cardUpdateRequestDTO,User user,Long boardId, Long columnId, Long cardId) {


        Card card= getCard(user.getId(),boardId,columnId,cardId);

        card.updateCard(cardUpdateRequestDTO);

        CardUpdateResponseDTO cardUpdateResponseDTO=CardUpdateResponseDTO.builder().card(card).build();

        return cardUpdateResponseDTO;
    }

    public void deleteCard(User user, Long boardId, Long columnId, Long cardId) {

        Card card= getCard(user.getId(),boardId,columnId,cardId);

        cardRepository.delete(card);
    }



    public Card getCard(Long userId, Long boardId, Long columnId, Long cardId){
        return cardRepository.findByUserIdAndBoardIdAndColumn_ColumnIdAndId(userId, boardId, columnId,cardId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 카드의 코멘트를 찾을 수 없습니다: "));
    }

    public Columns getColumn(Long columnId){
        return columnsRepository.findById(columnId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 columnId 입니다."));
    }

    public long getCardCountByColumn(Long columnId) {
        return cardRepository.countByColumn_ColumnId(columnId);
    }
}



