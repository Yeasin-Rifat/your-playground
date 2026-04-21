package com.your_playground.controller;

import com.your_playground.dto.LoginRequestDTO;
import com.your_playground.dto.user.*;
import com.your_playground.exception.errorhandler.ApiResponse;
import com.your_playground.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔥 CREATE (REGISTER)
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserResponseDTO>> create(
            @Valid @RequestBody UserRequestDTO request) {

        UserResponseDTO user = userService.createUser(request);

        return ResponseEntity.ok(
                new ApiResponse<>("User created successfully", user)
        );
    }

    // 🔥 LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDTO>> login(
            @RequestBody LoginRequestDTO request) {

        UserResponseDTO user = userService.login(request);

        return ResponseEntity.ok(
                new ApiResponse<>("Login successful", user)
        );
    }

    // 🔥 GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAll() {

        List<UserResponseDTO> users = userService.getAllUsers();

        return ResponseEntity.ok(
                new ApiResponse<>("Users fetched successfully", users)
        );
    }

    // 🔥 GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getById(
            @PathVariable Long id) {

        UserResponseDTO user = userService.getUserById(id);

        return ResponseEntity.ok(
                new ApiResponse<>("User fetched successfully", user)
        );
    }

    // 🔥 UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> update(
            @PathVariable Long id,
            @RequestBody UserRequestDTO request) {

        UserResponseDTO updatedUser = userService.updateUser(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>("Data updated successfully", updatedUser)
        );
    }

    // 🔥 DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> delete(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(
                new ApiResponse<>("User deleted successfully", null)
        );
    }
}