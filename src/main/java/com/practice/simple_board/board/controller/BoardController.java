package com.practice.simple_board.board.controller;

import com.practice.simple_board.board.service.BoardService;
import com.practice.simple_board.board.vo.BoardVO;
import com.practice.simple_board.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        MemberVO member = (MemberVO) session.getAttribute("member");

        System.out.println(member);

        boardService.create(boardVO, member.getMemberId());

        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(Model model) {

        List<BoardVO> boardList = boardService.selectAll();

        model.addAttribute("boardList", boardList);

        return "board/list";

    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,
                         Model model) {

        BoardVO board = boardService.selectOneById(id);

        boardService.increaseViewCount(board);

        model.addAttribute("board", board);

        return "board/detail";

    }

}
