package com.practice.simple_board.member.controller;

import com.practice.simple_board.member.service.MemberService;
import com.practice.simple_board.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute MemberVO memberVO,
                        BindingResult bindingResult,
                        HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "member/login";
        }

        MemberVO login = memberService.login(memberVO);

        if (login == null) {
            return "member/login";
        }

        session.setAttribute("member", login);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("member");
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/me/{memberId}")
    public String me(@PathVariable("memberId") String memberId,
                     Model model) {

        MemberVO member = memberService.selectMemberByMemberId(memberId);
        model.addAttribute("member", member);
        return "member/me";

    }

    @PostMapping("/me/{memberId}/update")
    public String memberUpdate(@Valid @ModelAttribute MemberVO memberVO,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/me";
        }

        if (!memberVO.getPassword().equals(memberVO.getPasswordCnf())) {
            return "member/me";
        }

        memberService.memberUpdate(memberVO);

        return "redirect:/member/login";
    }

    @GetMapping("/me/{memberId}/delete")
    public String memberDelete(@PathVariable("memberId") String memberId) {

        MemberVO member = memberService.selectMemberByMemberId(memberId);

        memberService.memberDelete(member);

        return "redirect:/member/login";
    }

}
