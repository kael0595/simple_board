package com.practice.simple_board.board.controller;

import com.practice.simple_board.board.service.BoardService;
import com.practice.simple_board.board.vo.BoardVO;
import com.practice.simple_board.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
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
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/create")
    public String createForm() {
        return "board/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute BoardVO boardVO,
                         BindingResult bindingResult,
                         HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "board/create";
        }

        MemberVO member =  (MemberVO) session.getAttribute("member");

        System.out.println(member);

        boardService.create(boardVO, member.getMemberId());

        return "redirect:/board/list";
    }

}
