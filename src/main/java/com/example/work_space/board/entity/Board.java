package com.example.work_space.board.entity;

import com.example.work_space.constants.BaseEntity;
import com.example.work_space.files.entity.AttachFile;
import com.example.work_space.list.entity.List;
import com.example.work_space.workspace.entity.WorkSpace;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Entity
@Getter
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String color;
    private String info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private WorkSpace workSpace;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<List> lists = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<AttachFile> attachFiles = new ArrayList<>();

    @Builder
    public Board(WorkSpace workSpace, String title, String color, String info) {
        this.workSpace = workSpace;
        this.title = title;
        this.color = color;
        this.info = info;
    }

    public Board() {
    }

    public Board updateBoard(String title, String color,  String info) {
        this.title = title;
        this.color = color;
        this.info = info;
        return this;
    }

    public void updateAttachFile(AttachFile attachFile) {
        this.attachFiles.clear();
        attachFiles.add(attachFile);
    }
}
