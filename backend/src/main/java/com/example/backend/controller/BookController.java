package com.example.backend.controller;


import com.example.backend.common.entity.Result;
import com.example.backend.entity.Book;
import com.example.backend.entity.dto.AddBookDto;
import com.example.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author demo
 * @since 2023-11-28
 */
@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public Result addBook(@RequestBody AddBookDto addBookDto){
        return bookService.addBook(addBookDto);
    }

    @GetMapping("/get")
    public Result getBook(Integer bookId){
        return bookService.getBook(bookId);
    }

    @GetMapping("/delete")
    public Result deleteBook(Integer bookId){
        return bookService.deleteBook(bookId);
    }

    @PostMapping("/update")
    public Result updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

    @GetMapping("/list")
    public Result getBookList(@RequestParam(required = false) String keyword){
        return bookService.getBookList(keyword);
    }

}
