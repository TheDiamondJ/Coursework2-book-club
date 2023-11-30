package com.example.backend.entity.dto;

import lombok.Data;


@Data
public class AddCommentDto {
    private Integer rating;

    private String content;

    private Integer bookId;

    private Integer userId;

}
