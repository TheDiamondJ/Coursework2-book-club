package com.example.backend.service;

import com.example.backend.common.entity.Result;
import com.example.backend.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.backend.entity.dto.AddBookDto;
import org.springframework.stereotype.Service;

/**
 *
 * @author demo
 * @since 2023-11-28
 */
@Service
public interface BookService extends IService<Book> {
    Result addBook(AddBookDto addBookDto);
    Result getBook(Integer bookId);
    Result deleteBook(Integer bookId);
    Result updateBook(Book book);
    Result getBookList(String keyword);

}
