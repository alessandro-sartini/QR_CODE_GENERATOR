package com.qr_generator.qr_code_gen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qr_generator.qr_code_gen.entity.QrCode;
import com.qr_generator.qr_code_gen.entity.User;

@Repository
public interface QrCodeRepo extends JpaRepository<QrCode, Long> {

    List<QrCode> findAllByUser(User user);

    Long countByUser(User user);

    List<QrCode> findAllByUserId(Long userId);
    
}
