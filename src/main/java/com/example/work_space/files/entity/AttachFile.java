package com.example.work_space.files.entity;

import com.example.work_space.board.entity.Board;
import com.example.work_space.card.entity.Card;
import com.example.work_space.constants.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class AttachFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String saved_name;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String ext;

    private boolean deleted = false;


    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public AttachFile(String originalFilename,String path) {
        String[] fileName = originalFilename.split("\\.");
        this.name = fileName[0];
        this.ext = fileName[1];
        this.saved_name= UUID.randomUUID().toString();

        this.path = path;
    }

    public void updateBoard(Board board) {
        this.board = board;
    }

    public AttachFile() {
    }

    public void softDelete() {
        this.deleted = true;
    }

    public void updateCard(Card savedCard) {
        this.card = savedCard;
    }
}
