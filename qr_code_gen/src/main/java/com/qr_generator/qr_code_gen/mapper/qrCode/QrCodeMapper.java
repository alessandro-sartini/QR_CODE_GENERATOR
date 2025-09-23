package com.qr_generator.qr_code_gen.mapper.qrCode;

import org.mapstruct.*;
import com.qr_generator.qr_code_gen.dto.qrCode.QrCodeGenerationDto;
import com.qr_generator.qr_code_gen.dto.qrCode.QrCodeResponseDto;
import com.qr_generator.qr_code_gen.dto.qrCode.QrCodeCustomizationDto;
import com.qr_generator.qr_code_gen.entity.QrCode;
import java.util.Map;
import java.util.HashMap;

@Mapper(componentModel = "spring")
public interface QrCodeMapper {

    QrCode toEntity(QrCodeGenerationDto dto);

    QrCodeResponseDto toDto(QrCode qrCode);

    // Metodo custom esplicitamente chiamato da MapStruct durante il mapping
    default Map<String, Object> map(QrCodeCustomizationDto customizationDto) {
        if (customizationDto == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("foregroundColor", customizationDto.getForegroundColor());
        map.put("backgroundColor", customizationDto.getBackgroundColor());
        map.put("logoUrl", customizationDto.getLogoUrl());
        map.put("format", customizationDto.getFormat());
        map.put("roundedCorners", customizationDto.getRoundedCorners());
        return map;
    }

    // Da Map a DTO (utile per output verso UI!)
    default QrCodeCustomizationDto map(Map<String, Object> customization) {
        if (customization == null) return null;
        QrCodeCustomizationDto dto = new QrCodeCustomizationDto();
        dto.setForegroundColor((String) customization.get("foregroundColor"));
        dto.setBackgroundColor((String) customization.get("backgroundColor"));
        dto.setLogoUrl((String) customization.get("logoUrl"));
        dto.setFormat((String) customization.get("format"));
        dto.setRoundedCorners((Boolean) customization.get("roundedCorners"));
        return dto;
    }
}
