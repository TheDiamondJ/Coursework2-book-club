package com.example.backend.service;

import com.example.backend.common.entity.Result;
import com.example.backend.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.entity.dto.AddCommentDto;
import org.springframework.stereotype.Service;

/**
 *
 * @author demo
 * @since 2023-11-28
 */
@Service
public interface CommentService extends IService<Comment> {

    Result addComment(AddCommentDto addCommentDto);
    Result getComment(Integer bookId);
    Result deleteComment(Integer commentId);
    Result updateComment(Comment comment);


    Result getCommentList(String keyword);
}
