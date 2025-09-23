package com.qr_generator.qr_code_gen.dto.qrCode;

import lombok.Data;

/**
 * DTO annidato per opzioni di personalizzazione QR.
 */
@Data
public class QrCodeCustomizationDto {
    private String foregroundColor; // "#000000"
    private String backgroundColor; // "#FFFFFF"
    private String logoUrl; // opzionale
    private String format; // "PNG", "SVG"
    private Boolean roundedCorners;
}
