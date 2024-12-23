package com.example.work_space.member.repository;

import com.example.work_space.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.id = :id AND m.deleted = false")
    Optional<Member> findActiveById(Long id); // 삭제되지 않은 회원만 조회

}
