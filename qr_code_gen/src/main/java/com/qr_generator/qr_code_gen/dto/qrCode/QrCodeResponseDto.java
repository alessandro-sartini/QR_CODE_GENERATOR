package com.qr_generator.qr_code_gen.dto.qrCode;

import java.time.Instant;
import java.util.Map;

import lombok.Data;

@Data
public class QrCodeResponseDto {

    private Long id;

    private String imgUrl;

    private Integer size;

    private Map<String, Object> customization;

    private Integer scanCount;

    private Instant createdAt;

    private Instant lastScannedAt;

}
