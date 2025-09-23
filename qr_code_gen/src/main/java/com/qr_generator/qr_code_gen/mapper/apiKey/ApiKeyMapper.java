package com.qr_generator.qr_code_gen.mapper.apiKey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.qr_generator.qr_code_gen.dto.apiKey.ApiKeyResponseDto;
import com.qr_generator.qr_code_gen.dto.user.UserRegistrationDto;
import com.qr_generator.qr_code_gen.dto.user.UserResponseDto;
import com.qr_generator.qr_code_gen.entity.ApiKey;
import com.qr_generator.qr_code_gen.entity.User;

@Mapper(componentModel = "spring")
public interface ApiKeyMapper {

    ApiKeyResponseDto toDto(ApiKey apiKey);

    ApiKey toEntity (ApiKeyResponseDto apiKeyResponseDto);

}
