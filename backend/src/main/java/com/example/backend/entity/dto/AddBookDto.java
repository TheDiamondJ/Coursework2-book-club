package com.example.backend.entity.dto;

import lombok.Data;

@Data
public class AddBookDto {
    private String image;

    private String title;

    private String publication;

    private String author;

    private String releaseTime;

    private Integer rating;

    private String content;
}
