package com.sparta.trello.card.service;

import com.sparta.trello.card.DTO.CardCreateRequestDTO;
import com.sparta.trello.card.DTO.CardCreateResponseDTO;
import com.sparta.trello.card.DTO.CardUpdateRequestDTO;
import com.sparta.trello.card.DTO.CardUpdateResponseDTO;
import com.sparta.trello.card.repository.CardRepository;
import com.sparta.trello.card.entity.Card;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Transactional
public class CardService {

    private final CardRepository cardRepository;
    public CardCreateResponseDTO createCard(CardCreateRequestDTO cardCreateRequestDTO) {

            Card card=Card.builder()
                          .cardCreateRequestDTO(cardCreateRequestDTO)
                          .build();

            cardRepository.save(card);

            CardCreateResponseDTO cardCreateResponseDTO=CardCreateResponseDTO.builder().card(card).build();

            return cardCreateResponseDTO;

    }

    public CardUpdateResponseDTO updateCard(CardUpdateRequestDTO cardUpdateRequestDTO, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("ID에 해당하는 카드를 찾을 수 없습니다: " + cardId));

        card.update(cardUpdateRequestDTO);

        CardUpdateResponseDTO cardUpdateResponseDTO=CardUpdateResponseDTO.builder().card(card).build();

        return cardUpdateResponseDTO;
    }

    public void deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NoSuchElementException("ID에 해당하는 카드를 찾을 수 없습니다: " + cardId));

        cardRepository.delete(card);
    }
}
