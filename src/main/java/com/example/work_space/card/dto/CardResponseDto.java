package com.example.work_space.card.dto;

import com.example.work_space.card.entity.Card;

import java.util.Date;

public class CardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private Date deadline;
    private String member;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.contents = card.getContents();
        this.deadline = card.getDeadline();
        this.member = card.getMember().getName();
    }
}
