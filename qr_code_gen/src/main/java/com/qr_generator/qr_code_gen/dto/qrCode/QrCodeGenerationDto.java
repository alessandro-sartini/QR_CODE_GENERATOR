package com.qr_generator.qr_code_gen.dto.qrCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * DTO per input generazione QR via POST.
 * Include dati, dimensione e personalizzazione.
 */
@Data
public class QrCodeGenerationDto {
    @NotBlank(message = "QR data cannot be blank")
    private String data;

    @NotNull
    @Min(value = 50)
    @Max(value = 2000)
    private Integer size; // pixel

    @NotNull
    private QrCodeCustomizationDto customization;
}
