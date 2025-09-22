package com.qr_generator.qr_code_gen.entity;

import java.time.Instant;
import java.util.Map;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import org.hibernate.type.SqlTypes;
import org.hibernate.annotations.JdbcTypeCode;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "qr_codes")
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "data", columnDefinition = "TEXT", nullable = false)
    private String data;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "size", nullable = false)
    private Integer size = 200;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "customization", columnDefinition = "json", nullable = true)
    private Map<String, Object> customization;

    @Column(name = "scan_count", nullable = false)
    private Integer scanCount = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "last_scanned_at")
    private Instant lastScannedAt;
}
