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

    // public UserResponseDto registrationUser( UserRegistrationDto
    // userRegistrationDto ){
    // User user = userMapper.toEntity(userRegistrationDto);
    // // user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
    // User saved = userRepo.save(user);

    // return userMapper.toDto(saved);
    // }

    public UserResponseDto registerUser(UserRegistrationDto dto) {

        User userRegistred = userMapper.toEntity(dto);
        
        userRegistred.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        
        userRepo.save(userRegistred);


        return userMapper.toDto(userRegistred);

    }

    // Registra nuovo utente, valida e-mail unica, hash password

    public UserResponseDto login(UserLoginDto dto) {

        // 1. Cerca utente per email
        Optional<User> userOpt = userRepo.findByEmail(dto.getEmail());
        if (userOpt.isEmpty()) {
            System.out.println("Mail not found");
        }
        User user = userOpt.get();
        // 2. Verifica password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            System.out.println("Password wrog!");
        }
        // 3. [Opzionale] Genera JWT/token, se previsto
        // 4. Restituisci i dati utente (mai inviare password)
        return userMapper.toDto(user);

    }
    // Verifica email/password, ritorna dati utente (JWT se previsto)

    UserResponseDto getProfileById(Long userId) {

        Optional<User> userFind = userRepo.findById(userId);

        if (userFind.isEmpty()) {
            System.out.println("User with ID: " + userId + " not found!");
        }

        User user = userFind.get();

        return userMapper.toDto(user);

    }
    // Recupera dati profilo, NO password

    Optional<User> getUserEntityById(Long id) {

        Optional<User> userFind = userRepo.findById(id);

        if (userFind.isEmpty()) {
            System.out.println("User with ID: " + id + " not found!");
        }

        return userFind;

    }
    // Per uso interno ai service collegati (es. SubscriptionService)

}
