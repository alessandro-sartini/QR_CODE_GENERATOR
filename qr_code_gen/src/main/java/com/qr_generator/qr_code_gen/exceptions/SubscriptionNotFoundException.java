package com.qr_generator.qr_code_gen.exceptions;

/**
 * Subscription not found exception.
 */
public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
