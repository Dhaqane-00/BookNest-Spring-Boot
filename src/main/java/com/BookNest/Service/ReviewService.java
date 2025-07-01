package com.BookNest.Service;

import com.BookNest.Models.Review;
import com.BookNest.Models.Book;
import com.BookNest.Models.User;
import com.BookNest.Repository.ReviewRepository;
import com.BookNest.Repository.BookRepository;
import com.BookNest.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserService userService;

    public ApiResponse<Review> createReview(Review review, String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ApiResponse.error("User not found", 404);
        }
        
        if (review.getBook() == null || review.getBook().getId() == null) {
            return ApiResponse.error("Book ID is required", 400);
        }
        
        Optional<Book> book = bookRepository.findById(review.getBook().getId());
        if (!book.isPresent()) {
            return ApiResponse.error("Book not found", 404);
        }
        
        // Check if user already reviewed this book
        if (reviewRepository.existsByUserAndBook(user, book.get())) {
            return ApiResponse.error("You have already reviewed this book", 409);
        }
        
        review.setUser(user);
        review.setBook(book.get());
        
        // Validate rating
        if (review.getRating() < 1 || review.getRating() > 5) {
            return ApiResponse.error("Rating must be between 1 and 5", 400);
        }
        
        Review savedReview = reviewRepository.save(review);
        return ApiResponse.created("Review created successfully", savedReview);
    }

    public ApiResponse<List<Review>> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return ApiResponse.success("Reviews retrieved successfully", reviews);
    }
    
    public ApiResponse<List<Review>> getReviewsByBook(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (!book.isPresent()) {
            return ApiResponse.error("Book not found", 404);
        }
        
        List<Review> reviews = reviewRepository.findByBook(book.get());
        return ApiResponse.success("Reviews retrieved successfully", reviews);
    }
    
    public ApiResponse<List<Review>> getReviewsByUser(String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ApiResponse.error("User not found", 404);
        }
        
        List<Review> reviews = reviewRepository.findByUser(user);
        return ApiResponse.success("Reviews retrieved successfully", reviews);
    }
    
    public ApiResponse<String> deleteReview(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (!review.isPresent()) {
            return ApiResponse.error("Review not found", 404);
        }
        
        reviewRepository.deleteById(id);
        return ApiResponse.success("Review deleted successfully", "Review with ID " + id + " has been deleted");
    }
    
    public boolean isReviewOwner(Long reviewId, String username) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            return review.get().getUser().getUsername().equals(username);
        }
        return false;
    }
}