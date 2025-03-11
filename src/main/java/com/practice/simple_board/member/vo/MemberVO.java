package com.practice.simple_board.member.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberVO {

    private Long id;

    private String memberId;

    private String password;

    private String passwordCnf;

    private String name;

    private String phone;

    private String email;

    private boolean deleted;

    private LocalDateTime createDt;

    private  LocalDateTime updateDt;
}
