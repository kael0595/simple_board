package com.practice.simple_board.board.vo;

import com.practice.simple_board.member.vo.MemberVO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardVO {

    private Long id;

    private String title;

    private String content;

    private String author;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;
}
