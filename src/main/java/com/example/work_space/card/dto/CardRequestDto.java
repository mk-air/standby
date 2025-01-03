package com.example.work_space.card.dto;

import lombok.Getter;


@Getter
public class CardRequestDto {
    private final String title;
    private final String contents;
    private final String deadline;
    private final String member; // member email
    private final Long listId;

    public CardRequestDto(String title, String contents, String deadline, String member, Long listId) {
        this.title = title;
        this.contents = contents;
        this.deadline = deadline;
        this.member = member;
        this.listId = listId;
    }

}
