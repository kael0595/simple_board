package com.example.demo.board.controller;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/create")
    public String createBoard(BoardDto boardDto) {
        return "board/create";
    }

    @PostMapping("/create")
    public String createBoard(@Valid BoardDto boardDto,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "board/create";
        }

        boardService.createBoard(boardDto);

        return "redirect:/board/list";
    }
}
