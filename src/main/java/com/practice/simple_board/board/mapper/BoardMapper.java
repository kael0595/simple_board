package com.practice.simple_board.board.mapper;

import com.practice.simple_board.board.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void create(BoardVO boardVO);

    List<BoardVO> selectAll();

}
