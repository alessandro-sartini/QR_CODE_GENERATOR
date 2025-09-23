package com.qr_generator.qr_code_gen.dto;

import java.time.Instant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;

    private String email;

    private String name;

    private String subscriptionPlan;

    private Instant createdAt;


}
