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

    public QrCodeResponseDto generateQrCode(QrCodeGenerationDto dto, Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        QrCode qrCode = qrCodeMapper.toEntity(dto);

        qrCode.setUser(user);
        qrCode.setScanCount(0);
        qrCode.setCreatedAt(Instant.now());
        qrCode.setImageUrl("https://placehold.co/" + qrCode.getSize() + "x" + qrCode.getSize() + ".png"); // simulato!

        QrCode saved = qrCodeRepo.save(qrCode);
        return qrCodeMapper.toDto(saved);
    }

    public List<QrCodeResponseDto> getQrsByUser(Long userId) {

        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("user not found");
        }

        List<QrCode> qrList = qrCodeRepo.findAllByUser(user.get());

        return qrList.stream()
                .map((qr) -> qrCodeMapper.toDto(qr))
                .toList();

    }

    public QrCodeResponseDto getQrById(Long qrId) {
        Optional<QrCode> found = qrCodeRepo.findById(qrId);

        if (found.isEmpty()) {
            throw new RuntimeException("qr not found");
        }

        return qrCodeMapper.toDto(found.get());
    }

    public boolean incrementScanCount(Long qrId) {
        // 1. Recupera il QR code
        Optional<QrCode> optQr = qrCodeRepo.findById(qrId);
        if (optQr.isEmpty()) {
            return false; // oppure lancia una eccezione custom
            // throw new QrCodeNotFoundException("QR code non trovato");
        }
        QrCode qrCode = optQr.get();

        // 2. Incrementa lo scan count
        qrCode.setScanCount(qrCode.getScanCount() + 1);

        // (opzionale: aggiorna anche lastScannedAt)
        qrCode.setLastScannedAt(Instant.now());

        // 3. Salva l’update
        qrCodeRepo.save(qrCode);

        // 4. Return ok
        return true;
    }

}
