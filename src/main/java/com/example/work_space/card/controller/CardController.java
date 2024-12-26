package com.example.work_space.card.controller;

import com.example.work_space.card.dto.CardDetailResponseDto;
import com.example.work_space.card.dto.CardRequestDto;
import com.example.work_space.card.dto.CardResponseDto;
import com.example.work_space.card.dto.CardSearchRequestDto;
import com.example.work_space.card.service.CardService;
import com.example.work_space.constants.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CardResponseDto>> createCard(@RequestBody CardRequestDto requestDto) {

        CardResponseDto responseDto = cardService.createCard(requestDto);
        return ResponseEntity.ok(new CommonResponse<>("카드 생성 완료", responseDto));
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CommonResponse<CardResponseDto>> updateCard(
            @RequestBody CardRequestDto requestDto, @PathVariable Long cardId) {

        CardResponseDto responseDto = cardService.updateCard(requestDto, cardId);
        return ResponseEntity.ok(new CommonResponse<>("카드 수정 완료", responseDto));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CommonResponse<CardDetailResponseDto>> getCard(
            @PathVariable Long cardId) {

        CardDetailResponseDto responseDto = cardService.getCardDetail(cardId);
        return ResponseEntity.ok(new CommonResponse<>("카드 상세 조회 완료", responseDto));
    }

    @DeleteMapping("/cardId")
    public ResponseEntity<CommonResponse<String>> deleteCard(
            @PathVariable Long cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.ok(new CommonResponse<>("카드 삭제 완료"));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CardResponseDto>>> searchCard(
            @RequestBody CardSearchRequestDto requestDto) {

        List<CardResponseDto> responseDtos = cardService.searchCard(requestDto);
        return ResponseEntity.ok(new CommonResponse<>("카드 검색 완료", responseDtos));
    }


}
