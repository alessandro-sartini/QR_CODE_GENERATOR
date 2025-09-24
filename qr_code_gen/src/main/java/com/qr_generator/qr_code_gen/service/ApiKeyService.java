package com.qr_generator.qr_code_gen.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.qr_generator.qr_code_gen.dto.apiKey.ApiKeyResponseDto;
import com.qr_generator.qr_code_gen.entity.ApiKey;
import com.qr_generator.qr_code_gen.entity.User;
import com.qr_generator.qr_code_gen.mapper.apiKey.ApiKeyMapper;
import com.qr_generator.qr_code_gen.repository.ApiKeyRepo;
import com.qr_generator.qr_code_gen.repository.UserRepo;
import com.qr_generator.qr_code_gen.exceptions.UserNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class ApiKeyService {

    private final ApiKeyRepo apiKeyRepository;
    private final ApiKeyMapper apiKeyMapper;
    private final UserRepo userRepo;

    public ApiKeyService(ApiKeyRepo apiKeyRepository, ApiKeyMapper apiKeyMapper, UserRepo userRepo) {
        this.apiKeyRepository = apiKeyRepository;
        this.apiKeyMapper = apiKeyMapper;
        this.userRepo = userRepo;
    }

    /**
     * Generates a new API key for a user.
     */
    public ApiKeyResponseDto generateForUser(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        String keyValue = generateUniqueKey();
        ApiKey apiKey = new ApiKey();
        apiKey.setUser(user);
        apiKey.setKeyValue(keyValue);
        apiKey.setActive(true);
        apiKey.setCreatedAt(Instant.now());
        ApiKey saved = apiKeyRepository.save(apiKey);
        return apiKeyMapper.toDto(saved);
    }

    private String generateUniqueKey() {
        String key;
        int attempt = 0;
        do {
            key = generateRandomKey(40);
            attempt++;
        } while (apiKeyRepository.findByKeyValue(key).isPresent() && attempt < 10);
        if (attempt == 10) {
            throw new IllegalStateException("Unable to generate a unique key after 10 attempts.");
        }
        return key;
    }

    private String generateRandomKey(int length) {
        SecureRandom random = new SecureRandom();
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        return sb.toString();
    }

    /**
     * Deactivates a key, only if it belongs to the user.
     */
    @Transactional
    public boolean revokeKey(String keyValue, Long userId) {
        Optional<ApiKey> key = apiKeyRepository.findByKeyValue(keyValue);
        if (key.isEmpty() || !key.get().getUser().getId().equals(userId))
            return false; // key does not belong to user
        ApiKey keyEnd = key.get();
        keyEnd.setActive(false);
        apiKeyRepository.save(keyEnd);
        return true;
    }

    /**
     * Returns all keys (active/inactive) for a user.
     */
    public List<ApiKeyResponseDto> getKeysByUser(Long userId) {
        List<ApiKey> listKey = apiKeyRepository.findAllByUser(userId);
        return listKey.stream()
            .map(apiKeyMapper::toDto)
            .collect(Collectors.toList());
    }
}
