package com.example.work_space.workspace_member.entity;

import com.example.work_space.member.entity.Member;
import com.example.work_space.workspace.entity.WorkSpace;
import com.example.work_space.workspace.type.WorkSpaceRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class WorkSpaceMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private WorkSpace workSpace;

    @Enumerated(EnumType.STRING)
    private WorkSpaceRole role;

    @Builder
    public WorkSpaceMember(Member member, WorkSpace workSpace, WorkSpaceRole role) {
        this.member = member;
        this.workSpace = workSpace;
        this.role = role;
    }

    protected WorkSpaceMember() {
    }

    public void changeRole(WorkSpaceRole role) {
        this.role = role;
    }
}
