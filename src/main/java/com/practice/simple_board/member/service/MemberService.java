package com.practice.simple_board.member.service;

import com.practice.simple_board.member.mapper.MemberMapper;
import com.practice.simple_board.member.vo.MemberVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public void join(MemberVO memberVO) {
        memberMapper.createMember(memberVO);
    }

    public int login(MemberVO memberVO) {
        return memberMapper.login(memberVO);
    }

    public MemberVO selectMemberById(int id) {
        return memberMapper.selectMemberById(id);
    }
}
