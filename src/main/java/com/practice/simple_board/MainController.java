package com.practice.simple_board;

import com.practice.simple_board.member.service.MemberService;
import com.practice.simple_board.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(Model model,
                        Principal principal) {

        if (principal.getName() != null) {

            MemberVO member = memberService.selectMemberByMemberId(principal.getName());

            model.addAttribute("member", member);
        }


        return "index";
    }
}
