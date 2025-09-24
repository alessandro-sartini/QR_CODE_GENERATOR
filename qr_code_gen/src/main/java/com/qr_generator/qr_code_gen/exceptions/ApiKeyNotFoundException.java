package com.qr_generator.qr_code_gen.exceptions;

/**
 * ApiKey not found exception.
 */
public class ApiKeyNotFoundException extends RuntimeException {
    public ApiKeyNotFoundException(String message) {
        super(message);
    }
}
