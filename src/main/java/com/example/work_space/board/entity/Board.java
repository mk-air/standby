package com.example.work_space.board.entity;

import com.example.work_space.constants.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String color;
    private String img;
    private String info;

    @Builder
    public Board(String title, String color, String img, String info) {
        this.title = title;
        this.color = color;
        this.img = img;
        this.info = info;
    }

    public Board() {
    }

    public Board update(String title, String color, String img, String info) {
        this.title = title;
        this.color = color;
        this.img = img;
        this.info = info;
        return this;
    }
}
