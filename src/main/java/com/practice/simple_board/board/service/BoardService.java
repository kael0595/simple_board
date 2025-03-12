package com.practice.simple_board.board.service;

import com.practice.simple_board.board.mapper.BoardMapper;
import com.practice.simple_board.board.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public void create(BoardVO boardVO, String author) {
        boardVO.setAuthor(author);
        boardMapper.create(boardVO);
    }
}
