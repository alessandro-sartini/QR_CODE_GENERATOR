package com.qr_generator.qr_code_gen.dto.user;

import java.time.Instant;
import lombok.Data;

/**
 * DTO for user responses (for profile, listing, etc.).
 * Does NOT expose password or sensitive fields!
 */
@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private String subscriptionPlan;
    private Instant createdAt;
}
