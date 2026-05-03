package com.your_playground.service.user;

import com.your_playground.dto.login.LoginRequestDTO;
import com.your_playground.dto.user.*;
import com.your_playground.entity.User;
import com.your_playground.exception.EmailAlreadyExistsException;
import com.your_playground.exception.UserNotFoundException;
import com.your_playground.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // ✅ constructor injection (BEST PRACTICE)
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔥 CREATE USER (SECURE)
    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // 🔥 PASSWORD ENCRYPTION
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        return mapToDTO(userRepository.save(user));
    }

    // 🔥 LOGIN (NEW)
    @Override
    public UserResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // 🔥 password match
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return mapToDTO(user);
    }

    // READ ALL
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // READ BY ID
    @Override
    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return mapToDTO(user);
    }

    // 🔥 UPDATE (SMART + SECURE)
    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // 🔹 NAME
        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }

        // 🔹 EMAIL (check only if changed)
        if (request.getEmail() != null && !request.getEmail().isBlank()) {

            if (!request.getEmail().equals(user.getEmail())) {

                boolean exists = userRepository.existsByEmail(request.getEmail());

                if (exists) {
                    throw new EmailAlreadyExistsException("Email already exists");
                }

                user.setEmail(request.getEmail());
            }
        }

        // 🔹 PASSWORD (encode)
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // 🔹 ROLE
        if (request.getRole() != null && !request.getRole().isBlank()) {
            user.setRole(request.getRole());
        }

        return mapToDTO(userRepository.save(user));
    }

    // DELETE
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(user);
    }

    // 🔥 DTO mapper
    private UserResponseDTO mapToDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;
    }
}