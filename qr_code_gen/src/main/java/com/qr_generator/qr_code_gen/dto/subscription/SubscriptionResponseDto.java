package com.qr_generator.qr_code_gen.dto.subscription;

import java.time.Instant;
import java.time.LocalDate;

public class SubscriptionResponseDto {
    
    private Long userId;

    private Long id;

    private String planType;

    private String status;

    private Instant createdAt;

    private LocalDate startDate;

    private LocalDate endDate;

}
