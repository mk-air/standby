package com.example.work_space.card.repository;

import com.example.work_space.card.entity.Card;
import com.example.work_space.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM Card c WHERE c.title LIKE :searchWord ORDER BY c.title ASC")
    List<Card> findAllByTitle(String searchWord);

    @Query("SELECT c FROM Card c WHERE c.contents LIKE :searchWord ORDER BY c.contents ASC")
    List<Card> findAllByContents(String searchWord);

    List<Card> findAllByDeadline(Date deadline);

    List<Card> findAllByMember(Member member);
}
