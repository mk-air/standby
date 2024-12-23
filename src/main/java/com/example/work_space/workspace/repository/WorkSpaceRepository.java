package com.example.work_space.workspace.repository;

import com.example.work_space.workspace.entity.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Long> {
}
