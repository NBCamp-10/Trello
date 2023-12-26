package com.sparta.trello.card.controller;

import com.sparta.trello.card.DTO.CardCreateRequestDTO;
import com.sparta.trello.card.DTO.CardCreateResponseDTO;
import com.sparta.trello.card.DTO.CardUpdateRequestDTO;
import com.sparta.trello.card.DTO.CardUpdateResponseDTO;
import com.sparta.trello.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cards")
@RestController
@RequiredArgsConstructor//이 애너테이션은 주로 Java 클래스에서 생성자를 자동으로 생성하는 데 사용됩니다.
public class CardController {
    private final CardService cardService;
    @PostMapping
    public CardCreateResponseDTO createCard (@RequestBody CardCreateRequestDTO cardCreateRequestDTO){
            return cardService.createCard(cardCreateRequestDTO);
    }

    @PatchMapping("/{cardId}")
    public CardUpdateResponseDTO updateCard (@PathVariable Long cardId,
                                             @RequestBody CardUpdateRequestDTO CardUpdateRequestDTO){
        return cardService.updateCard(CardUpdateRequestDTO,cardId);
    }

}
