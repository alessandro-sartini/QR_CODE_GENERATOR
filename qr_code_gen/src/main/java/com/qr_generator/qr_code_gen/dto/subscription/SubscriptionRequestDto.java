package com.qr_generator.qr_code_gen.dto.subscription;

import lombok.Data;

@Data
public class SubscriptionRequestDto {

    private Long userId;

    private String planType;

}
