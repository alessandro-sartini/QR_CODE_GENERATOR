package com.qr_generator.qr_code_gen.mapper.user;

import org.mapstruct.*;
import com.qr_generator.qr_code_gen.dto.user.UserRegistrationDto;
import com.qr_generator.qr_code_gen.dto.user.UserResponseDto;
import com.qr_generator.qr_code_gen.entity.User;

/**
 * Mapper for converting User <-> DTO (MapStruct).
 * Handles only struct fields mapping, never passwords!
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);

    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "subscriptionPlan", constant = "FREE")
    User toEntity(UserRegistrationDto dto);

    // For update: create a mapping if you use a UserUpdateDto, with @BeanMapping(nullValuePropertyMappingStrategy = ...)
}
