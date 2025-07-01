package com.BookNest.Controllers;

import com.BookNest.Service.UserService;
import com.BookNest.utils.ApiResponse;
import com.BookNest.Models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> loginUser(@RequestBody User user) {
        ApiResponse<User> response = userService.loginUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        ApiResponse<User> response = userService.createUser(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        ApiResponse<List<User>> response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}