package com.practice.simple_board.member.vo;

import lombok.Data;

@Data
public class MemberVO {

    private Long id;

    private String memberId;

    private String password;

    private String passwordCnf;

    private String name;

    private String phone;

    private String email;
}
