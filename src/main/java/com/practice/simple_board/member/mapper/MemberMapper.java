package com.practice.simple_board.member.mapper;

import com.practice.simple_board.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void createMember(MemberVO memberVO);

    MemberVO login(MemberVO memberVO);

    MemberVO selectMemberById(int id);

    MemberVO selectMemberByMemberId(String memberId);

    void memberUpdate(MemberVO memberVO);

    void memberDelete(MemberVO member);
}
