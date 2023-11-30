package com.example.backend.entity.vo;

import com.example.backend.entity.Book;
import lombok.Data;

import java.util.List;

@Data
public class BookVo {
    private Book book;
    private Integer commentCount;
    private List<CommentVo> commentList;
}
