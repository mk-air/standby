package com.example.work_space.card.entity;

import com.example.work_space.constants.BaseEntity;
import com.example.work_space.list.entity.List;
import com.example.work_space.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class Card extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    private String contents;
    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    private List list;

    @Builder
    public Card(String title, String contents, Date deadline, Member member, List list) {
        this.title = title;
        this.contents = contents;
        this.deadline = deadline;
        this.member = member;
        this.list = list; 
    }

    public Card update(String title, String contents, Date deadline, Member member) {
        this.title = title;
        this.contents = contents;
        this.deadline = deadline;
        this.member = member;
        return this;
    }

    protected Card() {
    }
}
