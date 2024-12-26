package com.example.work_space.card.dto;

import lombok.Getter;


@Getter
public class CardRequestDto {
    private String title;
    private String contents;
    private String deadline;
    private String member; // member email
    private Long listId;

    public CardRequestDto(String title, String contents, String deadline, String member, Long listId) {
        this.title = title;
        this.contents = contents;
        this.deadline = deadline;
        this.member = member;
        this.listId = listId;
    }

}
