package com.practice.simple_board.member.service;

import com.practice.simple_board.member.mapper.MemberMapper;
import com.practice.simple_board.member.vo.MemberRole;
import com.practice.simple_board.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberVO member = memberMapper.selectMemberByMemberId(username);

        if (member == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        List<GrantedAuthority> auth = new ArrayList<>();

        if (member.getMemberId().startsWith("admin")) {
            auth.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            auth.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
        }
        return new User(member.getMemberId(), member.getPassword(), auth);
    }
}
