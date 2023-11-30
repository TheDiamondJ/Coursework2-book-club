package com.example.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.common.entity.Result;
import com.example.backend.common.utils.ParseDateTime;
import com.example.backend.entity.Book;
import com.example.backend.entity.Comment;
import com.example.backend.entity.User;
import com.example.backend.entity.dto.AddBookDto;
import com.example.backend.entity.vo.BookVo;
import com.example.backend.entity.vo.CommentVo;
import com.example.backend.mapper.BookMapper;
import com.example.backend.mapper.CommentMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author demo
 * @since 2023-11-28
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    private final BookMapper bookMapper;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    @Autowired
    public BookServiceImpl(BookMapper bookMapper, CommentMapper commentMapper, UserMapper userMapper) {
        this.bookMapper = bookMapper;
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Result addBook(AddBookDto addBookDto) {
        Book book = new Book();
        BeanUtils.copyProperties(addBookDto,book);
        return Result.succ(bookMapper.insert(book));
    }

    @Override
    public Result getBook(Integer bookId) {
        Book book = bookMapper.selectById(bookId);
        BookVo bookVo = new BookVo();
        bookVo.setBook(book);
        List<Comment> commentList = commentMapper.selectList(null);
        List<CommentVo> commentVoList = new LinkedList<>();
        for (Comment comment : commentList) {
            CommentVo commentVo = new CommentVo();
            commentVo.setTime(ParseDateTime.parse(comment.getTime()));
            commentVo.setRating(comment.getRating());
            commentVo.setContent(comment.getContent());
            User user = userMapper.selectById(comment.getUserId());
            commentVo.setUser(user);
            commentVoList.add(commentVo);
        }
        bookVo.setCommentList(commentVoList);
        bookVo.setCommentCount(commentVoList.size());
        return Result.succ(bookVo);
    }

    @Override
    public Result deleteBook(Integer bookId) {
        return Result.succ(bookMapper.deleteById(bookId));
    }

    @Override
    public Result updateBook(Book book) {
        return Result.succ(bookMapper.updateById(book));
    }

    @Override
    public Result getBookList(String keyword) {
        if (keyword == null){
            return Result.succ(bookMapper.selectList(
                    new QueryWrapper<Book>()
                            .orderByDesc("rating")
            ));
        }
        return Result.succ(
                bookMapper.selectList(
                        new QueryWrapper<Book>()
                                .like("title",keyword)
                                .or()
                                .like("author",keyword)
                                .or()
                                .like("publication",keyword)
                                .orderByDesc("rating")
                )
        );
    }
}
