package com.BookNest.Controllers;

import com.BookNest.Models.Book;
import com.BookNest.Service.BookService;
import com.BookNest.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> createBook(@RequestBody Book book) {
        ApiResponse<Book> response = bookService.createBook(book);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        ApiResponse<List<Book>> response = bookService.getAllBooks();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
