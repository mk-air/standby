package com.example.work_space.workspace.type;

public enum WorkSpaceRole {
    ADMIN("admin"),      // 워크스페이스 관리자
    MEMBER("member"),     // 보드 관리 가능
    READ_ONLY("readOnly")  ; // 조회만 가능

    private final String role;

    WorkSpaceRole(String role) {
        this.role = role;
    }
}
