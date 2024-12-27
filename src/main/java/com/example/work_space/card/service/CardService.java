package com.example.work_space.card.service;

import com.example.work_space.card.dto.CardDetailResponseDto;
import com.example.work_space.card.dto.CardRequestDto;
import com.example.work_space.card.dto.CardResponseDto;
import com.example.work_space.card.dto.CardSearchRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CardService {
    CardResponseDto createCard(CardRequestDto requestDto, Long authId, MultipartFile file);

    CardResponseDto updateCard(CardRequestDto requestDto, Long cardId, Long authId, MultipartFile file);

    CardDetailResponseDto getCardDetail(Long cardId);

    void deleteCard(Long cardId, Long authId);

    List<CardResponseDto> searchCard(CardSearchRequestDto requestDto);
}
