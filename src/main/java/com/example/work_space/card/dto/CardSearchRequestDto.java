package com.example.work_space.card.dto;

import lombok.Getter;

@Getter
public class CardSearchRequestDto {
    private String sortBy;
    private String searchWord;

    public CardSearchRequestDto(String sortBy, String searchWord) {
        this.sortBy = sortBy;
        this.searchWord = searchWord;
    }
}
