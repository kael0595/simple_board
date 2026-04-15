package com.example.demo.member.dto;

import lombok.Data;

@Data
public class MemberUpdateDto {

    private String currentPassword;

    private String newPassword;

    private String newPasswordCnf;

    private String nickname;

    private String name;

    private String phone;
}
