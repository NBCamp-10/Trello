package com.sparta.trello.card.controller;

import com.sparta.trello.card.DTO.*;
import com.sparta.trello.card.service.CardService;
import com.sparta.trello.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cards")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    // 카드 생성 API
    @PostMapping("/{boardId}/{columnId}")
    public CardCreateResponseDTO createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody CardCreateRequestDTO cardCreateRequestDTO,
                                            @PathVariable Long boardId,
                                            @PathVariable Long columnId) {
        return cardService.createCard(cardCreateRequestDTO, userDetails.getUser(), boardId, columnId);
    }

    // 카드 업데이트 API
    @PatchMapping("/{boardId}/{columnId}/{cardId}")
    public CardUpdateResponseDTO updateCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @PathVariable Long boardId,
                                            @PathVariable Long columnId,
                                            @PathVariable Long cardId,
                                            @RequestBody CardUpdateRequestDTO cardUpdateRequestDTO) {
        return cardService.updateCard(cardUpdateRequestDTO, userDetails.getUser(), boardId, columnId, cardId);
    }

    // 카드 삭제 API
    @DeleteMapping("/{boardId}/{columnId}/{cardId}")
    public String deleteCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                             @PathVariable Long boardId,
                             @PathVariable Long columnId,
                             @PathVariable Long cardId) {
        cardService.deleteCard(userDetails.getUser(), boardId, columnId, cardId);
        return "삭제완료";
    }

    // 카드 위치 변경 API
    @PatchMapping("/move1/{boardId}/{columnId}/{cardId}")
    public String moveInColumn(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @PathVariable Long boardId,
                           @PathVariable Long columnId,
                           @PathVariable Long cardId,
                           @RequestBody CardMoveRequestDTO cardMoveRequestDTO) {
        cardService.moveInColumn(cardMoveRequestDTO, userDetails.getUser(), boardId, columnId, cardId);
        return "이동완료";
    }

    // 카드 이동 API (다른 컬럼으로 이동)
    @PatchMapping("/move/{boardId}/{columnId}/{cardId}")
    public String moveCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @PathVariable Long boardId,
                           @PathVariable Long columnId,
                           @PathVariable Long cardId,
                           @RequestBody CardMoveRequestDTO cardMoveRequestDTO) {
        cardService.moveCard(cardMoveRequestDTO, userDetails.getUser(), boardId, columnId, cardId);
        return "이동완료";
    }
}
