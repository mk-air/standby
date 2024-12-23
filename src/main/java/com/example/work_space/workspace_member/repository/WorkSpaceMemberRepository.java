package com.example.work_space.workspace_member.repository;

import com.example.work_space.workspace_member.entity.WorkSpaceMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkSpaceMemberRepository extends JpaRepository<WorkSpaceMember, Long> {
    Page<WorkSpaceMember> findByWorkSpaceId(Long workspaceId, Pageable pageable);

    @Query("SELECT wm FROM WorkSpaceMember wm " +
            "JOIN FETCH wm.member " +
            "JOIN FETCH wm.workSpace " +
            "WHERE wm.workSpace.id = :workSpaceId AND wm.member.id = :memberId")
    Optional<WorkSpaceMember> findByWorkSpaceIdAndMemberId(@Param("workSpaceId") Long workSpaceId,
                                                                    @Param("memberId") Long memberId);
    boolean existsByWorkSpaceIdAndMemberId(Long workSpaceId, Long memberId);
}
