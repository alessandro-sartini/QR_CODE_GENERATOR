package com.qr_generator.qr_code_gen.service;

import org.springframework.stereotype.Service;

import com.qr_generator.qr_code_gen.dto.user.UserRegistrationDto;
import com.qr_generator.qr_code_gen.dto.user.UserResponseDto;
import com.qr_generator.qr_code_gen.entity.User;
import com.qr_generator.qr_code_gen.mapper.user.UserMapper;
import com.qr_generator.qr_code_gen.repository.UserRepo;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final UserRepo userRepo;

    public UserService(UserRepo userRepository, UserMapper userMapper) {
        this.userRepo = userRepository;
        this.userMapper = userMapper;
    }

    // public UserResponseDto registrationUser( UserRegistrationDto
    // userRegistrationDto ){
    // User user = userMapper.toEntity(userRegistrationDto);
    // // user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
    // User saved = userRepo.save(user);

    // return userMapper.toDto(saved);
    // }

}
