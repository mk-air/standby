package com.example.work_space.list.repository;

import com.example.work_space.list.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<List, Long> {

    java.util.List<List> findByBoardIdOrderBySeqAsc(Long boardId);
}
