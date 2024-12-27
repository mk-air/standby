package com.example.work_space.card.dto;

import com.example.work_space.card.entity.Card;
import lombok.Getter;

import java.text.SimpleDateFormat;

@Getter
public class CardResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String  deadline;
    private String member;
    private Long imgId;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.contents = card.getContents();
        if (card.getDeadline() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            this.deadline = formatter.format(card.getDeadline());
        }
        if (card.getMember() != null) {
            this.member = card.getMember().getName();
        }

        if (card.getAttachFiles().size() > 0) {
            this.imgId = card.getAttachFiles().get(0).getId();
        }else {
            this.imgId = null;
        }
    }
}
