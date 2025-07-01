package com.BookNest.Service;

import com.BookNest.Repository.BookRepository;
import com.BookNest.Service.UserService;
import com.BookNest.utils.ApiResponse;
import com.BookNest.Models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.BookNest.Models.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserService userService;

    public ApiResponse<Book> createBook(Book book, String username) {
        User creator = userService.findByUsername(username);
        if (creator == null) {
            return ApiResponse.error("Creator not found", 404);
        }
        
        book.setCreatedBy(creator);
        Book savedBook = bookRepository.save(book);
        return ApiResponse.created("Book created successfully", savedBook);
    }

    public ApiResponse<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ApiResponse.success("Books retrieved successfully", books);
    }
    
    public ApiResponse<Book> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ApiResponse.success("Book retrieved successfully", book.get());
        }
        return ApiResponse.error("Book not found", 404);
    }
    
    public ApiResponse<Book> updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBookOpt = bookRepository.findById(id);
        if (!existingBookOpt.isPresent()) {
            return ApiResponse.error("Book not found", 404);
        }
        
        Book existingBook = existingBookOpt.get();
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setDescription(updatedBook.getDescription());
        existingBook.setCoverImageUrl(updatedBook.getCoverImageUrl());
        
        Book savedBook = bookRepository.save(existingBook);
        return ApiResponse.success("Book updated successfully", savedBook);
    }
    
    public ApiResponse<String> deleteBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            return ApiResponse.error("Book not found", 404);
        }
        
        bookRepository.deleteById(id);
        return ApiResponse.success("Book deleted successfully", "Book with ID " + id + " has been deleted");
    }
    
    public ApiResponse<List<Book>> getBooksByCreator(String username) {
        User creator = userService.findByUsername(username);
        if (creator == null) {
            return ApiResponse.error("Creator not found", 404);
        }
        
        List<Book> books = bookRepository.findByCreatedBy(creator);
        return ApiResponse.success("Books retrieved successfully", books);
    }
    
    public boolean isBookOwner(Long bookId, String username) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return book.get().getCreatedBy().getUsername().equals(username);
        }
        return false;
    }
}
