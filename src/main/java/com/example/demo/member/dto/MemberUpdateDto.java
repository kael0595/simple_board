package com.example.demo.member.dto;

import lombok.Data;

@Data
public class MemberUpdateDto {

    private String email;

    private String password;

    private String passwordCnf;

    private String nickname;

    private String name;

    private String phone;
}
