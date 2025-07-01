package com.BookNest.Service;

import com.BookNest.Repository.UserRepository;
import com.BookNest.utils.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.BookNest.Models.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ApiResponse<User> loginUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            return ApiResponse.error("User not found", 404);
        }
        if (!existingUser.getPassword().equals(user.getPassword())) {
            return ApiResponse.error("Invalid password", 401);
        }
        return ApiResponse.success("User logged in successfully", existingUser);
    }

    public ApiResponse<User> createUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            return ApiResponse.error("User already exists", 409);
        }
        User savedUser = userRepository.save(user);
        return ApiResponse.created("User created successfully", savedUser);
    }

    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ApiResponse.success("Users retrieved successfully", users);
    }
}