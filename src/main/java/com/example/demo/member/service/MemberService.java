package com.example.demo.member.service;

import com.example.demo.member.dto.MemberDto;
import com.example.demo.member.dto.Role;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public void signup(MemberDto memberDto) {

        if (!memberDto.getPassword().equals(memberDto.getPasswordCnf())) {
            throw new RuntimeException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setNickname(memberDto.getNickname());
        if (member.getNickname().equals("admin")) {
            member.setRole(Role.ADMIN);
        } else {
            member.setRole(Role.USER);
        }
        memberRepository.save(member);
    }

    public Member findByNickName(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    public void memberUpdate(Member member, MemberDto memberDto) {

        if (memberDto.getNickname() != null && !memberDto.getNickname().equals("") && !memberDto.getNickname().equals(member.getNickname())) {
            member.setNickname(memberDto.getNickname());
        }

        if (memberDto.getName() != null && !memberDto.getName().equals("") && !memberDto.getName().equals(member.getName())) {
            member.setName(memberDto.getName());
        }

        if (memberDto.getPhone() != null && !memberDto.getPhone().equals("") && !memberDto.getPhone().equals(member.getPhone())) {
            member.setPhone(memberDto.getPhone());
        }

        memberRepository.save(member);

    }

}
