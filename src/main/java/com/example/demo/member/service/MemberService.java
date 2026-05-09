package com.example.demo.member.service;

import com.example.demo.member.dto.MemberDto;
import com.example.demo.member.dto.MemberUpdateDto;
import com.example.demo.member.dto.Role;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void memberUpdate(Member member, MemberUpdateDto memberUpdateDto) {

        if (memberUpdateDto.getNickname() != null && !memberUpdateDto.getNickname().equals("") && !memberUpdateDto.getNickname().equals(member.getNickname())) {
            member.setNickname(memberUpdateDto.getNickname());
        }

        if (memberUpdateDto.getName() != null && !memberUpdateDto.getName().equals("") && !memberUpdateDto.getName().equals(member.getName())) {
            member.setName(memberUpdateDto.getName());
        }

        if (memberUpdateDto.getPhone() != null && !memberUpdateDto.getPhone().equals("") && !memberUpdateDto.getPhone().equals(member.getPhone())) {
            member.setPhone(memberUpdateDto.getPhone());
        }

        memberRepository.save(member);

    }

    public void updatePassword(Member member, MemberUpdateDto dto) {

        if (!passwordEncoder.matches(dto.getCurrentPassword(), member.getPassword())) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (!dto.getNewPassword().equals(dto.getNewPasswordCnf())) {
            throw new RuntimeException("새 비밀번호가 일치하지 않습니다.");
        }

        member.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        memberRepository.save(member);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow();
    }

    public boolean existByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
