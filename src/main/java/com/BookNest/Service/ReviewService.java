package com.BookNest.Service;

import com.BookNest.Models.Review;
import com.BookNest.Repository.ReviewRepository;
import com.BookNest.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public ApiResponse<Review> createReview(Review review) {
        Review savedReview = reviewRepository.save(review);
        return ApiResponse.created("Review created successfully", savedReview);
    }

    public ApiResponse<List<Review>> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return ApiResponse.success("Reviews retrieved successfully", reviews);
    }
}