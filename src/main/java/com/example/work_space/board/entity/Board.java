package com.example.work_space.board.entity;

import com.example.work_space.constants.BaseEntity;
import com.example.work_space.workspace.entity.WorkSpace;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private WorkSpace workSpace;

    @Builder
    public Board(WorkSpace workSpace, String title, String color, String img, String info) {
        this.workSpace = workSpace;
        this.title = title;
        this.color = color;
        this.img = img;
        this.info = info;
    }

    public Board() {
    }

    public Board updateBoard(String title, String color, String img, String info) {
        this.title = title;
        this.color = color;
        this.img = img;
        this.info = info;
        return this;
    }
}
