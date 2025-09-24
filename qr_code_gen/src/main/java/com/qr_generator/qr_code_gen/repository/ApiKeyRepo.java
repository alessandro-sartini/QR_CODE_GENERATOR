package com.qr_generator.qr_code_gen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qr_generator.qr_code_gen.entity.ApiKey;
import com.qr_generator.qr_code_gen.entity.User;

@Repository
public interface ApiKeyRepo extends JpaRepository<ApiKey, Long> {

    Optional<ApiKey> findByKeyValue(String keyValue);

    List<ApiKey> findAllByUser(User user);
}
