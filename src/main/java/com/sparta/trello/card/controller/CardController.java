package com.sparta.trello.card.controller;

import com.sparta.trello.card.DTO.*;
import com.sparta.trello.card.service.CardService;
import com.sparta.trello.common.dto.CommonResponseDTO;
import com.sparta.trello.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RequestMapping("/api/cards")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    // 카드 생성 API
    @PostMapping("/{boardId}/{columnId}")
    public ResponseEntity<CommonResponseDTO> createCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @RequestBody CardCreateRequestDTO cardCreateRequestDTO,
                                            @PathVariable Long boardId,
                                            @PathVariable Long columnId) {
        try {
            cardService.createCard(cardCreateRequestDTO, userDetails.getUser(), boardId, columnId);
            return ResponseEntity.ok().body(new CommonResponseDTO("카드 생성 완료.", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 카드 업데이트 API
    @PatchMapping("/{boardId}/{columnId}/{cardId}")
    public ResponseEntity<CommonResponseDTO> updateCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                            @PathVariable Long boardId,
                                            @PathVariable Long columnId,
                                            @PathVariable Long cardId,
                                            @RequestBody CardUpdateRequestDTO cardUpdateRequestDTO) {

        try {
            cardService.updateCard(cardUpdateRequestDTO, userDetails.getUser(), boardId, columnId, cardId);
            return ResponseEntity.ok().body(new CommonResponseDTO("카드 이름 수정 완료.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 카드 삭제 API
    @DeleteMapping("/{boardId}/{columnId}/{cardId}")
    public  ResponseEntity<CommonResponseDTO> deleteCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                         @PathVariable Long boardId,
                                                         @PathVariable Long columnId,
                                                         @PathVariable Long cardId) {
        try {
            cardService.deleteCard(userDetails.getUser(), boardId, columnId, cardId);
            return ResponseEntity.ok().body(new CommonResponseDTO("카드 삭제 완료.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 카드 위치 변경 API
    @PatchMapping("/moveInColumn/{boardId}/{columnId}/{cardId}")
    public ResponseEntity<CommonResponseDTO> moveInColumn(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @PathVariable Long boardId,
                               @PathVariable Long columnId,
                               @PathVariable Long cardId,
                               @RequestBody CardMoveRequestDTO cardMoveRequestDTO) {

        try {
            cardService.moveInColumn(cardMoveRequestDTO, userDetails.getUser(), boardId, columnId, cardId);
            return ResponseEntity.ok().body(new CommonResponseDTO("카드 이동 완료.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 카드 이동 API (다른 컬럼으로 이동)
    @PatchMapping("/move/{boardId}/{columnId}/{cardId}")
    public ResponseEntity<CommonResponseDTO> moveCard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @PathVariable Long boardId,
                           @PathVariable Long columnId,
                           @PathVariable Long cardId,
                           @RequestBody CardMoveRequestDTO cardMoveRequestDTO) {

        try {
            cardService.moveCard(cardMoveRequestDTO, userDetails.getUser(), boardId, columnId, cardId);
            return ResponseEntity.ok().body(new CommonResponseDTO("카드 이동 완료.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
