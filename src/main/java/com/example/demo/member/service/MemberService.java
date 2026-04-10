package com.example.demo.member.service;

import com.example.demo.member.dto.MemberDto;
import com.example.demo.member.dto.Role;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
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

        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.setNickname(memberDto.getNickname());
        if (member.getEmail().startsWith("admin")) {
            member.setRole(Role.ADMIN);
        } else {
            member.setRole(Role.USER);
        }
        member.setVerified(true);
        member.setCreatedAt(LocalDateTime.now());
        memberRepository.save(member);
    }

    public Member findByNickName(String nickname) {
        return memberRepository.findByNickname(nickname);
    }
}
