package com.sparta.trello.card.service;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.service.BoardService;
import com.sparta.trello.card.DTO.*;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.repository.CardRepository;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.columns.repository.ColumnsRepository;
import com.sparta.trello.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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

        if (cardCreateRequestDTO.getTitle() == null || cardCreateRequestDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("카드 제목을 입력하세요.");
        }
        if (cardCreateRequestDTO.getText() == null || cardCreateRequestDTO.getText().isEmpty()) {
            throw new IllegalArgumentException("카드 내용을 입력하세요.");
        }
        if (cardCreateRequestDTO.getWorker() == null || cardCreateRequestDTO.getWorker().isEmpty()) {
            throw new IllegalArgumentException("작업자를 입력하세요.");
        }
        if (cardCreateRequestDTO.getColor() == null || cardCreateRequestDTO.getColor().isEmpty()) {
            throw new IllegalArgumentException("색깔을 입력하세요.");
        }
        if (cardCreateRequestDTO.getDeadline() == null) {
            throw new IllegalArgumentException("마감일을 입력하세요.");
        }

        // 특정 컬럼에 속하는 카드의 개수를 조회하고 1을 더한후 새로 생성될 카드의 인덱스에 대입 합니다.
        Long cardIndex = getCardCountByColumn(columnId) + 1L;

        Card card = Card.builder()
                .cardIndex(cardIndex)
                .board(board)
                .column(column)
                .user(user)
                .title(cardCreateRequestDTO.getTitle())
                .text(cardCreateRequestDTO.getText())
                .color(cardCreateRequestDTO.getColor())
                .worker(cardCreateRequestDTO.getWorker())
                .deadline(cardCreateRequestDTO.getDeadline())
                .build();

        cardRepository.save(card);

        CardCreateResponseDTO cardCreateResponseDTO = CardCreateResponseDTO.builder().card(card).build();

        return cardCreateResponseDTO;

    }

    public CardUpdateResponseDTO updateCard(CardUpdateRequestDTO cardUpdateRequestDTO, User user, Long boardId, Long columnId, Long cardId) {


        Card card = getCard(user.getId(), boardId, columnId, cardId);

        card.updateCard(cardUpdateRequestDTO);

        CardUpdateResponseDTO cardUpdateResponseDTO = CardUpdateResponseDTO.builder().card(card).build();

        return cardUpdateResponseDTO;
    }

    public void deleteCard(User user, Long boardId, Long columnId, Long cardId) {

        Card card = getCard(user.getId(), boardId, columnId, cardId);

        cardRepository.delete(card);
    }

    public void moveInColumn(CardMoveRequestDTO cardMoveRequestDTO, User user, Long boardId, Long columnId, Long cardId) {
        // 이동하려는 카드 가져오기
        Card movingCard = getCard(user.getId(), boardId, columnId, cardId);

        Long targetCardIndex = cardMoveRequestDTO.getTargetCardIndex();

        // getColumnCards 메서드를 사용하여 해당 열의 모든 카드를 가져옵니다.
        List<Card> cardsInColumn = getColumnCards(user.getId(), boardId, columnId);

        // 이동하려는 카드의 현재 인덱스
        Long currentCardIndex = movingCard.getCardIndex();

        for (Card card : cardsInColumn) {
            Long cardIndex = card.getCardIndex();

            if (currentCardIndex < targetCardIndex) {
                // 이동한 카드가 타겟 위치보다 아래로 이동한 경우
                if (cardIndex > currentCardIndex && cardIndex <= targetCardIndex) {
                    // 현재 위치보다 아래에 있고 타겟 위치 이상의 카드의 인덱스를 1씩 감소시킵니다.
                    card.moveInColumn(cardIndex - 1);
                }
            } else if (currentCardIndex > targetCardIndex) {
                // 이동한 카드가 타겟 위치보다 위로 이동한 경우
                if (cardIndex >= targetCardIndex && cardIndex < currentCardIndex) {
                    // 타겟 위치 이상이고 현재 위치 미만의 카드의 인덱스를 1씩 증가시킵니다.
                    card.moveInColumn(cardIndex + 1);
                }
            }
        }

        // 타겟 위치에 이동한 카드를 설정
        movingCard.moveInColumn(targetCardIndex);
    }


    public void moveCard(CardMoveRequestDTO cardMoveRequestDTO, User user, Long boardId, Long columnId, Long cardId) {
        // 이동하려는 카드 가져오기
        Card movingCard = getCard(user.getId(), boardId, columnId, cardId);

        // 목표로 하는 컬럼 아이디 가져오기
        Long targetColumnId = cardMoveRequestDTO.getTargetColumnId();

        Columns column = getColumn(targetColumnId);

        // 이동하려는 카드의 현재 인덱스
        Long currentCardIndex = movingCard.getCardIndex();

        // 특정 컬럼에 속하는 카드의 개수를 조회하고 1을 더한후 새로 생성될 카드의 인덱스에 대입 합니다.
        Long cardIndex = getCardCountByColumn(targetColumnId) + 1L;

        // 이동하려는 컬럼과 목표 컬럼이 동일하지 않을 경우에만 이동 수행
        if (!columnId.equals(targetColumnId)) {
            // 이동하려는 컬럼 정보 업데이트
            movingCard.moveCard(column, cardIndex);
        }

        // getColumnCards 메서드를 사용하여 해당 열의 모든 카드를 가져옵니다.
        List<Card> cardsInColumn = getColumnCards(user.getId(), boardId, columnId);

        for (Card card : cardsInColumn) {
            Long cardIndex2 = card.getCardIndex();
            if (cardIndex2 > currentCardIndex) {
                // 현재 위치보다 아래에 있고 타겟 위치 이상의 카드의 인덱스를 1씩 감소시킵니다.
                card.moveInColumn(cardIndex2 - 1);
            }
        }
    }

    public Card getCard(Long userId, Long boardId, Long columnId, Long cardId) {
        return cardRepository.findByUserIdAndBoardIdAndColumn_ColumnIdAndId(userId, boardId, columnId, cardId)
                .orElseThrow(() -> new NoSuchElementException("해당하는 카드를 찾을 수 없습니다: "));
    }

    public Columns getColumn(Long columnId) {
        return columnsRepository.findById(columnId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 columnId 입니다."));
    }

    public long getCardCountByColumn(Long columnId) {
        return cardRepository.countByColumn_ColumnId(columnId);
    }

    public List<Card> getColumnCards(Long userId, Long boardId, Long columnId) {
        return cardRepository.findByUserIdAndBoardIdAndColumn_ColumnIdOrderByCardIndex(userId, boardId, columnId);
    }

}



