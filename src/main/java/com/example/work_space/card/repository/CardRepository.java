package com.example.work_space.card.repository;

import com.example.work_space.card.entity.Card;
import com.example.work_space.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByTitle(String searchWord);

    List<Card> findAllByContents(String searchWord);

    List<Card> findAllByDeadline(Date deadline);

    List<Card> findAllByMember(Member member);
}
