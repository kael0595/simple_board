package com.practice.simple_board.member.service;

import com.practice.simple_board.member.mapper.MemberMapper;
import com.practice.simple_board.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    private final MemberMapper memberMapper;

    public void join(MemberVO memberVO) {

        memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));

        memberMapper.createMember(memberVO);
    }

    public MemberVO login(MemberVO memberVO) {
        return memberMapper.login(memberVO);
    }

    public MemberVO selectMemberById(int id) {
        return memberMapper.selectMemberById(id);
    }

    public MemberVO selectMemberByMemberId(String memberId) {
        return memberMapper.selectMemberByMemberId(memberId);
    }

    public void memberUpdate(MemberVO memberVO) {
        memberMapper.memberUpdate(memberVO);
    }

    public void memberDelete(MemberVO member) {
        memberMapper.memberDelete(member);
    }
}
