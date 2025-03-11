package com.practice.simple_board.member.controller;

import com.practice.simple_board.member.service.MemberService;
import com.practice.simple_board.member.vo.MemberVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm() {
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute MemberVO memberVO,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        if (!memberVO.getPassword().equals(memberVO.getPasswordCnf())) {
            return "member/join";
        }

        memberService.join(memberVO);

        return "redirect:/member/login";

    }
}
