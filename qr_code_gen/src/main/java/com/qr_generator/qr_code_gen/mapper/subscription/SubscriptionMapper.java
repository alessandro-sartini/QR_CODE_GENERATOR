package com.qr_generator.qr_code_gen.mapper.subscription;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.qr_generator.qr_code_gen.dto.subscription.SubscriptionRequestDto;
import com.qr_generator.qr_code_gen.dto.subscription.SubscriptionResponseDto;
import com.qr_generator.qr_code_gen.entity.Subscription;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "stripeCustomerId", ignore = true)
    @Mapping(target = "stripeSubscriptionId", ignore = true)
    @Mapping(target = "subscriptionPlan", ignore = true)
    @Mapping(target = "user", ignore = true)
    Subscription toEntity(SubscriptionRequestDto dto);

    SubscriptionResponseDto toDto(Subscription qrCode);

}
