package com.BookNest.Controllers;

import com.BookNest.Models.Book;
import com.BookNest.Service.BookService;
import com.BookNest.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AUTHOR')")
    public ResponseEntity<ApiResponse<Book>> createBook(@RequestBody Book book, Authentication authentication) {
        ApiResponse<Book> response = bookService.createBook(book, authentication.getName());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        ApiResponse<List<Book>> response = bookService.getAllBooks();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {
        ApiResponse<Book> response = bookService.getBookById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('AUTHOR') and @bookService.isBookOwner(#id, authentication.name))")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable Long id, @RequestBody Book book) {
        ApiResponse<Book> response = bookService.updateBook(id, book);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('AUTHOR') and @bookService.isBookOwner(#id, authentication.name))")
    public ResponseEntity<ApiResponse<String>> deleteBook(@PathVariable Long id) {
        ApiResponse<String> response = bookService.deleteBook(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/my-books")
    @PreAuthorize("hasRole('AUTHOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Book>>> getMyBooks(Authentication authentication) {
        ApiResponse<List<Book>> response = bookService.getBooksByCreator(authentication.getName());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
