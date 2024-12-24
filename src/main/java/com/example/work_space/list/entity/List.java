package com.example.work_space.list.entity;

import com.example.work_space.board.entity.Board;
import com.example.work_space.constants.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class List extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    public List(Board board, String title, Long seq) {
        this.board = board;
        this.title = title;
        this.seq = seq;
    }

    public List() {
    }

    public List updateTitle(String title) {
        this.title = title;
        return this;
    }
    public List updateSeq(Long seq) {
        this.seq = seq;
        return this;
    }

    public void downSeq(){
        this.seq--;
    }

    public void upSeq(){
        this.seq++;
    }
}
