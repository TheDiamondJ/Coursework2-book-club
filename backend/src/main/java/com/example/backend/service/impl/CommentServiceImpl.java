package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.common.entity.Result;
import com.example.backend.entity.Comment;
import com.example.backend.entity.dto.AddCommentDto;
import com.example.backend.mapper.CommentMapper;
import com.example.backend.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author demo
 * @since 2023-11-28
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public Result addComment(AddCommentDto addCommentDto) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(addCommentDto,comment);
        comment.setTime(LocalDateTime.now());
        return Result.succ(commentMapper.insert(comment));
    }

    @Override
    public Result getComment(Integer bookId) {
        List<Comment> commentList = commentMapper.selectList(new QueryWrapper<Comment>().eq("book_id", bookId));
        return Result.succ(commentList);
    }

    @Override
    public Result deleteComment(Integer commentId) {
        return Result.succ(commentMapper.deleteById(commentId));
    }

    @Override
    public Result updateComment(Comment comment) {
        return Result.succ(commentMapper.updateById(comment));
    }

    @Override
    public Result getCommentList(String keyword) {
        if (keyword == null) {
            return Result.succ(commentMapper.selectList(null));
        }
        return Result.succ(commentMapper.selectList(new QueryWrapper<Comment>().like("content", keyword)));
    }
}
