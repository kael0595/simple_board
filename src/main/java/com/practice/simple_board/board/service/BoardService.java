package com.practice.simple_board.board.service;

import com.practice.simple_board.board.mapper.BoardMapper;
import com.practice.simple_board.board.vo.BoardVO;
import com.practice.simple_board.member.vo.MemberVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public void create(BoardVO boardVO, String author) {
        boardVO.setAuthor(author);
        boardMapper.create(boardVO);
    }

    public List<BoardVO> selectAll() {
        return boardMapper.selectAll();
    }

    public BoardVO selectOneById(Long id) {
        return boardMapper.selectOneById(id);
    }

    public void increaseViewCount(BoardVO board) {
        board.setViewCount(board.getViewCount() + 1);
        boardMapper.increaseViewCount(board);
    }

    public void updateBoard(BoardVO board, BoardVO boardVO) {

        board.setTitle(boardVO.getTitle());
        board.setContent(boardVO.getContent());
        boardMapper.updateBoard(board);
    }

    public void deleteBoard(BoardVO boardVO) {
        boardVO.setDeleted(true);
        boardMapper.deleteBoard(boardVO);
    }
}
