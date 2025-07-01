package com.BookNest.Controllers;

import com.BookNest.Models.Review;
import com.BookNest.Service.ReviewService;
import com.BookNest.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('AUTHOR')")
    public ResponseEntity<ApiResponse<Review>> createReview(@RequestBody Review review, Authentication authentication) {
        ApiResponse<Review> response = reviewService.createReview(review, authentication.getName());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Review>>> getAllReviews() {
        ApiResponse<List<Review>> response = reviewService.getAllReviews();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsByBook(@PathVariable Long bookId) {
        ApiResponse<List<Review>> response = reviewService.getReviewsByBook(bookId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/my-reviews")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('AUTHOR')")
    public ResponseEntity<ApiResponse<List<Review>>> getMyReviews(Authentication authentication) {
        ApiResponse<List<Review>> response = reviewService.getReviewsByUser(authentication.getName());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @reviewService.isReviewOwner(#id, authentication.name)")
    public ResponseEntity<ApiResponse<String>> deleteReview(@PathVariable Long id) {
        ApiResponse<String> response = reviewService.deleteReview(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}