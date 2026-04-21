package com.your_playground.controller;

import com.your_playground.dto.user.UserRequestDTO;
import com.your_playground.dto.user.UserResponseDTO;
import com.your_playground.exception.errorhandler.ApiResponse;
import com.your_playground.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping("/create")
    public UserResponseDTO create(@RequestBody UserRequestDTO request) {
        return userService.createUser(request);
    }

    // GET ALL
    @GetMapping
    public List<UserResponseDTO> getAll() {
        return userService.getAllUsers();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public UserResponseDTO getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ApiResponse<UserResponseDTO> update(@PathVariable Long id,
                                               @RequestBody UserRequestDTO request) {

        UserResponseDTO updatedUser = userService.updateUser(id, request);

        return new ApiResponse<>(
                "Data updated successfully",
                updatedUser
        );
    }

    // DELETE
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}