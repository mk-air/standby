package com.example.work_space.card.controller;

import com.example.work_space.auth.model.Authentication;
import com.example.work_space.card.dto.CardDetailResponseDto;
import com.example.work_space.card.dto.CardRequestDto;
import com.example.work_space.card.dto.CardResponseDto;
import com.example.work_space.card.dto.CardSearchRequestDto;
import com.example.work_space.card.service.CardService;
import com.example.work_space.constants.CommonResponse;
import com.example.work_space.constants.GlobalConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CardResponseDto>> createCard(@RequestPart CardRequestDto requestDto,
                                                                      @RequestPart(required = false)MultipartFile file,
                                                                      HttpServletRequest request) {
        HttpSession session =request.getSession(false);

        Authentication authentication = (Authentication) session.getAttribute(GlobalConstants.USER_AUTH);
        Long authId = authentication.getId();

        CardResponseDto responseDto = cardService.createCard(requestDto, authId,file);
        return ResponseEntity.ok(new CommonResponse<>("카드 생성 완료", responseDto));
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CommonResponse<CardResponseDto>> updateCard(@RequestPart CardRequestDto requestDto,
                                                                      @PathVariable Long cardId,
                                                                      @RequestPart(required = false) MultipartFile file,
                                                                      HttpServletRequest request) {
        HttpSession session =request.getSession(false);

        Authentication authentication = (Authentication) session.getAttribute(GlobalConstants.USER_AUTH);
        Long authId = authentication.getId();

        CardResponseDto responseDto = cardService.updateCard(requestDto, cardId, authId,file);
        return ResponseEntity.ok(new CommonResponse<>("카드 수정 완료", responseDto));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CommonResponse<CardDetailResponseDto>> getCard(@PathVariable Long cardId) {

        CardDetailResponseDto responseDto = cardService.getCardDetail(cardId);
        return ResponseEntity.ok(new CommonResponse<>("카드 상세 조회 완료", responseDto));
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CommonResponse<String>> deleteCard(@PathVariable Long cardId, HttpServletRequest request) {
        HttpSession session =request.getSession(false);

        Authentication authentication = (Authentication) session.getAttribute(GlobalConstants.USER_AUTH);
        Long authId = authentication.getId();

        cardService.deleteCard(cardId, authId);
        return ResponseEntity.ok(new CommonResponse<>("카드 삭제 완료"));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<CardResponseDto>>> searchCard(
            @RequestBody CardSearchRequestDto requestDto) {

        List<CardResponseDto> responseDtos = cardService.searchCard(requestDto);
        return ResponseEntity.ok(new CommonResponse<>("카드 검색 완료", responseDtos));
    }


}
