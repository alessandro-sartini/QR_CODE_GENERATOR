package com.qr_generator.qr_code_gen.mapper.apiKey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.qr_generator.qr_code_gen.dto.apiKey.ApiKeyResponseDto;
import com.qr_generator.qr_code_gen.entity.ApiKey;

@Mapper(componentModel = "spring")
public interface ApiKeyMapper {

    @Mapping(target = "userId", source = "user.id")
    ApiKeyResponseDto toDto(ApiKey apiKey);

    ApiKey toEntity(ApiKeyResponseDto apiKeyResponseDto);

}
