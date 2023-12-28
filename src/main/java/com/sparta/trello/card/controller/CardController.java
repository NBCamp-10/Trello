package com.sparta.trello.card.controller;

import com.sparta.trello.card.DTO.CardCreateRequestDTO;
import com.sparta.trello.card.DTO.CardCreateResponseDTO;
import com.sparta.trello.card.DTO.CardUpdateRequestDTO;
import com.sparta.trello.card.DTO.CardUpdateResponseDTO;
import com.sparta.trello.card.service.CardService;
import com.sparta.trello.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cards")
@RestController
@RequiredArgsConstructor//이 애너테이션은 주로 Java 클래스에서 생성자를 자동으로 생성하는 데 사용됩니다.
public class CardController {
    private final CardService cardService;
    @PostMapping("/{boardId}/{columnId}")
    public CardCreateResponseDTO createCard (@AuthenticationPrincipal UserDetailsImpl userDetail,
                                             @RequestBody CardCreateRequestDTO cardCreateRequestDTO,
                                             @PathVariable Long boardId,
                                             @PathVariable Long columnId){
            return cardService.createCard(cardCreateRequestDTO,userDetail.getUser(),boardId, columnId);
    }

    @PatchMapping("/{boardId}/{columnId}/{cardId}")
    public CardUpdateResponseDTO updateCard (@AuthenticationPrincipal UserDetailsImpl userDetail,
                                             @PathVariable Long boardId,
                                             @PathVariable Long columnId,
                                             @PathVariable Long cardId,
                                             @RequestBody CardUpdateRequestDTO CardUpdateRequestDTO){
        return cardService.updateCard(CardUpdateRequestDTO,userDetail.getUser(),boardId,columnId,cardId);
    }

    @DeleteMapping("/{boardId}/{columnId}/{cardId}")
    public String deleteCard (@AuthenticationPrincipal UserDetailsImpl userDetail,
                              @PathVariable Long boardId,
                              @PathVariable Long columnId,
                              @PathVariable Long cardId){
        cardService.deleteCard(userDetail.getUser(),boardId,columnId,cardId);
        return "삭제완료";
    }

}
