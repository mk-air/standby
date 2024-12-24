package com.example.work_space.card.controller;

import com.example.work_space.card.dto.CardRequestDto;
import com.example.work_space.card.dto.CardResponseDto;
import com.example.work_space.card.service.CardService;
import com.example.work_space.constants.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
