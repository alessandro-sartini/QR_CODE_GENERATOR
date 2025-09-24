package com.qr_generator.qr_code_gen.exceptions;

/**
 * User not found exception.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
