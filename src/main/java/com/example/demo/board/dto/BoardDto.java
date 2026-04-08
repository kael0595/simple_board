package com.example.demo.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {

    @NotBlank
    private String name;

    private String description;

    @NonNull
    private Type boardType;

    private boolean active = true;
}
