package com.BookNest.Controllers;

import com.BookNest.Models.Review;
import com.BookNest.Service.ReviewService;
import com.BookNest.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<Review>> createReview(@RequestBody Review review) {
        ApiResponse<Review> response = reviewService.createReview(review);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Review>>> getAllReviews() {
        ApiResponse<List<Review>> response = reviewService.getAllReviews();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}