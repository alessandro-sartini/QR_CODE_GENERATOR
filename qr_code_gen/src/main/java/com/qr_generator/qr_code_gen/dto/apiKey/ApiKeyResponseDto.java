package com.qr_generator.qr_code_gen.dto.apiKey;

import java.time.Instant;

import lombok.Data;

@Data
public class ApiKeyResponseDto {

    private Long id;

    private Long userId;

    private String keyValue;

    private Boolean active;

    private Instant createdAt;
    
    private Instant lastUsedAt;


}
