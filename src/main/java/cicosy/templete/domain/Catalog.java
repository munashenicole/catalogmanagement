package cicosy.templete.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "catalogs", indexes = {
        @Index(name = "idx_catalog_code", columnList = "code", unique = true),
        @Index(name = "idx_catalog_status", columnList = "status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CatalogStatus status;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "effective_from")
    private LocalDateTime effectiveFrom;

    @Column(name = "effective_to")
    private LocalDateTime effectiveTo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = CatalogStatus.DRAFT;
        }
        if (effectiveFrom == null && status == CatalogStatus.ACTIVE) {
            effectiveFrom = LocalDateTime.now();
        }
        active = (status == CatalogStatus.ACTIVE);
        createdAt = LocalDateTime.now();
        createdDate = LocalDateTime.now();
        lastModifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        active = (status == CatalogStatus.ACTIVE);
        lastModifiedDate = LocalDateTime.now();
    }
}