package com.example.work_space.workspace.entity;

import com.example.work_space.member.entity.Member;
import com.example.work_space.workspace_member.entity.WorkSpaceMember;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class WorkSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "workSpace", cascade = CascadeType.ALL)
    private List<WorkSpaceMember> workSpaceMembers = new ArrayList<>();

    @Builder
    public WorkSpace(String name, String description, List<WorkSpaceMember> workSpaceMembers) {
        this.name = name;
        this.description = description;
        this.workSpaceMembers = workSpaceMembers;
    }

    protected WorkSpace() {
    }

    public WorkSpace update(WorkSpace updatedWorkSpace) {
        if (updatedWorkSpace.name != null) {
            this.name = updatedWorkSpace.name;
        }
        if (updatedWorkSpace.description != null) {
            this.description = updatedWorkSpace.description;
        }
        return this;
    }
}
