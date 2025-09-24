package com.qr_generator.qr_code_gen.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qr_generator.qr_code_gen.dto.qrCode.QrCodeGenerationDto;
import com.qr_generator.qr_code_gen.dto.qrCode.QrCodeResponseDto;
import com.qr_generator.qr_code_gen.entity.QrCode;
import com.qr_generator.qr_code_gen.entity.User;
import com.qr_generator.qr_code_gen.mapper.qrCode.QrCodeMapper;
import com.qr_generator.qr_code_gen.repository.QrCodeRepo;
import com.qr_generator.qr_code_gen.repository.UserRepo;
import com.qr_generator.qr_code_gen.exceptions.UserNotFoundException;
import com.qr_generator.qr_code_gen.exceptions.QrCodeNotFoundException;

@Service
public class QrCodeService {

    private final QrCodeMapper qrCodeMapper;
    private final QrCodeRepo qrCodeRepo;
    private final UserRepo userRepo;

    @Autowired
    public QrCodeService(QrCodeMapper qrCodeMapper, QrCodeRepo qrCodeRepo, UserRepo userRepo) {
        this.qrCodeMapper = qrCodeMapper;
        this.qrCodeRepo = qrCodeRepo;
        this.userRepo = userRepo;
    }

    /**
     * Generates a QR code for a user.
     */
    public QrCodeResponseDto generateQrCode(QrCodeGenerationDto dto, Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        QrCode qrCode = qrCodeMapper.toEntity(dto);
        qrCode.setUser(user);
        qrCode.setScanCount(0);
        qrCode.setCreatedAt(Instant.now());
        qrCode.setImageUrl("https://placehold.co/" + qrCode.getSize() + "x" + qrCode.getSize() + ".png"); // simulated
        QrCode saved = qrCodeRepo.save(qrCode);
        return qrCodeMapper.toDto(saved);
    }

    /**
     * Returns all QR codes for a user.
     */
    public List<QrCodeResponseDto> getQrsByUser(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<QrCode> qrList = qrCodeRepo.findAllByUser(user);
        return qrList.stream()
            .map(qrCodeMapper::toDto)
            .toList();
    }

    /**
     * Gets a QR code by ID.
     */
    public QrCodeResponseDto getQrById(Long qrId) {
        QrCode found = qrCodeRepo.findById(qrId)
            .orElseThrow(() -> new QrCodeNotFoundException("QR not found"));
        return qrCodeMapper.toDto(found);
    }

    /**
     * Increments scan count by 1.
     */
    public boolean incrementScanCount(Long qrId) {
        QrCode qrCode = qrCodeRepo.findById(qrId)
            .orElseThrow(() -> new QrCodeNotFoundException("QR code not found"));
        qrCode.setScanCount(qrCode.getScanCount() + 1);
        qrCode.setLastScannedAt(Instant.now());
        qrCodeRepo.save(qrCode);
        return true;
    }
}
