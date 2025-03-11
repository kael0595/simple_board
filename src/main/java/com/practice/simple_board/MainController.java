package com.practice.simple_board;

import com.practice.simple_board.member.service.MemberService;
import com.practice.simple_board.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(Model model,
                        HttpSession session) {

        if (session.getAttribute("member") != null) {

            MemberVO member = (MemberVO) session.getAttribute("member");

            model.addAttribute("member", member);
        }


        return "index";
    }
}
