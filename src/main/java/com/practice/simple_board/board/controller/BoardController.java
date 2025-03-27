package com.practice.simple_board.board.controller;

import com.practice.simple_board.board.service.BoardService;
import com.practice.simple_board.board.vo.BoardVO;
import com.practice.simple_board.member.service.MemberService;
import com.practice.simple_board.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    private final MemberService memberService;

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

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id,
                             Model model,
                             Principal principal) {

        MemberVO member = memberService.selectMemberByMemberId(principal.getName());

        BoardVO board = boardService.selectOneById(id);

        if (!member.getMemberId().equals(board.getAuthor())) {
            return "redirect:/board/detail/" + id;
        }

        model.addAttribute("board", board);

        return "board/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @ModelAttribute BoardVO boardVO,
                         BindingResult bindingResult,
                         Principal principal) {

        MemberVO member = memberService.selectMemberByMemberId(principal.getName());

        BoardVO board = boardService.selectOneById(id);

        if (!member.getMemberId().equals(board.getAuthor())) {
            return "redirect:/board/detail/" + id;
        }

        if (bindingResult.hasErrors()) {
            return "board/update";
        }

        boardService.updateBoard(board, boardVO);

        return "redirect:/board/detail/" + id;

    }

    @GetMapping("/delete/{id}")
    public String boardDelete(@PathVariable("id") Long id,
                              Principal principal) {

        MemberVO member = memberService.selectMemberByMemberId(principal.getName());

        BoardVO boardVO = boardService.selectOneById(id);

        if (!boardVO.getAuthor().equals(member.getMemberId())) {
            return "redirect:/board/detail/" + id;
        }

        boardService.deleteBoard(boardVO);

        return "redirect:/board/list";

    }

}
