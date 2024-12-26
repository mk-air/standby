package com.example.work_space.card.service;

import com.example.work_space.card.dto.CardDetailResponseDto;
import com.example.work_space.card.dto.CardRequestDto;
import com.example.work_space.card.dto.CardResponseDto;
import com.example.work_space.card.dto.CardSearchRequestDto;

import java.util.List;

public interface CardService {
    CardResponseDto createCard(CardRequestDto requestDto);

    CardResponseDto updateCard(CardRequestDto requestDto, Long cardId);

    CardDetailResponseDto getCardDetail(Long cardId);

    void deleteCard(Long cardId);
}
