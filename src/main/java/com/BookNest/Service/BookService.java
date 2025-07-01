package com.BookNest.Service;

import com.BookNest.Repository.BookRepository;
import com.BookNest.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.BookNest.Models.Book;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public ApiResponse<Book> createBook(Book book) {
        Book savedBook = bookRepository.save(book);
        return ApiResponse.created("Book created successfully", savedBook);
    }

    public ApiResponse<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ApiResponse.success("Books retrieved successfully", books);
    }
}
