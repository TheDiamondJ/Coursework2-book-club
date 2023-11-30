package com.example.backend.entity.vo;

import com.example.backend.entity.User;
import lombok.Data;

@Data
public class CommentVo {
    private User user;
    private Integer rating;
    private String content;
    private String time;
}
