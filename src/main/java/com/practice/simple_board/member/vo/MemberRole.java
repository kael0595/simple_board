package com.practice.simple_board.member.vo;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private final String value;

    MemberRole(String value) {
        this.value = value;
    }

}
