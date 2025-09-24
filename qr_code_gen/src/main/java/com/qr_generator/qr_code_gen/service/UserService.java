package com.qr_generator.qr_code_gen.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qr_generator.qr_code_gen.dto.user.UserLoginDto;
import com.qr_generator.qr_code_gen.dto.user.UserRegistrationDto;
import com.qr_generator.qr_code_gen.dto.user.UserResponseDto;
import com.qr_generator.qr_code_gen.entity.User;
import com.qr_generator.qr_code_gen.mapper.user.UserMapper;
import com.qr_generator.qr_code_gen.repository.UserRepo;
import com.qr_generator.qr_code_gen.exceptions.InvalidInputException;
import com.qr_generator.qr_code_gen.exceptions.UserNotFoundException;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user, validates unique email, hashes password.
     */
    public UserResponseDto registerUser(UserRegistrationDto dto) {
        User userRegistered = userMapper.toEntity(dto);
        userRegistered.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        userRepo.save(userRegistered);
        return userMapper.toDto(userRegistered);
    }

    /**
     * Checks email/password, returns user data (JWT if implemented).
     */
    public UserResponseDto login(UserLoginDto dto) {
        Optional<User> userOpt = userRepo.findByEmail(dto.getEmail());
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("Email not found");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new InvalidInputException("Incorrect password");
        }

        return userMapper.toDto(user);
    }

    /**
     * Gets user profile by ID, NO password returned.
     */
    public UserResponseDto getProfileById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    /**
     * For internal service use (e.g. SubscriptionService)
     */
    public Optional<User> getUserEntityById(Long id) {
        Optional<User> userFind = userRepo.findById(id);
        if (userFind.isEmpty()) {
            throw new UserNotFoundException("User with ID: " + id + " not found!");
        }
        return userFind;
    }
}
