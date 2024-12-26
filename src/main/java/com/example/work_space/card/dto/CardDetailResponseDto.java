package com.example.work_space.card.dto;

import com.example.work_space.card.entity.Card;

import java.util.Date;

public class CardDetailResponseDto {
    private Long id;
    private String title;
    private String contents;
    private Date deadline;
    private String member;
//    private List<Comment> comment;

    public CardDetailResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.contents = card.getContents();
        this.deadline = card.getDeadline();
        this.member = card.getMember().getName();
//        this.comment = card.getComment;
    }
}