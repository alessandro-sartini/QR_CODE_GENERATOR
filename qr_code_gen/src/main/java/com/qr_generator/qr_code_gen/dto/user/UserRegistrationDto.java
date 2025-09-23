package com.qr_generator.qr_code_gen.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for user registration requests.
 * Only the fields required for account creation are exposed.
 */
@Data
public class UserRegistrationDto {

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is mandatory")
    @Size(max = 255, message = "Email max 255 characters")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 64, message = "Password must be 8-64 characters")
    private String password;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name max 100 characters")
    private String name;
}
