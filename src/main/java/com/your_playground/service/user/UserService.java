package com.your_playground.service.user;

import com.your_playground.dto.user.UserRequestDTO;
import com.your_playground.dto.user.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, UserRequestDTO request);
    void deleteUser(Long id);
}
