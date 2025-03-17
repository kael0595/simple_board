package com.practice.simple_board.board.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardVO {

    private Long id;

    private String title;

    private String content;

    private String author;

    private int viewCount;

    private boolean deleted;

    private LocalDateTime createDt;

    private LocalDateTime updateDt;
}
