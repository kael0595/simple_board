package com.example.demo.board.controller;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
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

    @GetMapping
    public ResponseEntity<List<BoardDto>> boardList() {

        List<BoardDto> boardDtoList = boardService.findAll()
                .stream()
                .map(board -> {
                    BoardDto dto = new BoardDto();
                    dto.setBoardType(board.getBoardType());
                    dto.setName(board.getName());
                    dto.setDescription(board.getDescription());
                    dto.setActive(board.isActive());
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(boardDtoList);
    }
}
