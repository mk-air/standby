package com.example.work_space.card.service;

import com.example.work_space.card.dto.CardRequestDto;
import com.example.work_space.card.dto.CardResponseDto;

public interface CardService {
    CardResponseDto createCard(CardRequestDto requestDto);
    CardResponseDto updateCard(CardRequestDto requestDto, Long cardId);
}
