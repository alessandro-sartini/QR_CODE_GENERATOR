package com.qr_generator.qr_code_gen.exceptions;

/**
 * QR code not found exception.
 */
public class QrCodeNotFoundException extends RuntimeException {
    public QrCodeNotFoundException(String message) {
        super(message);
    }
}
