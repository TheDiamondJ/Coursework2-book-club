package com.example.backend.controller;


import com.example.backend.common.entity.Result;
import com.example.backend.entity.Comment;
import com.example.backend.entity.dto.AddCommentDto;
import com.example.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author demo
 * @since 2023-11-28
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public Result addComment(@RequestBody AddCommentDto addCommentDto){
        return commentService.addComment(addCommentDto);
    }

    @GetMapping("/get")
    public Result getComment(Integer bookId){
        return commentService.getComment(bookId);
    }

    @DeleteMapping("/delete")
    public Result deleteComment(Integer commentId){
        return commentService.deleteComment(commentId);
    }

    @PostMapping("/update")
    public Result updateComment(Comment comment){
        return commentService.updateComment(comment);
    }

    @GetMapping("/list")
    public Result getCommentList(@RequestParam(required = false) String keyword){
        return commentService.getCommentList(keyword);
    }



}
