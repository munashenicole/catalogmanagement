package cicosy.templete.domain.dto;

import cicosy.templete.domain.CatalogStatus;
import java.time.LocalDateTime;

public record CatalogDTO(
    Long id,
    String code,
    String name,
    String description,
    CatalogStatus status,
    LocalDateTime effectiveFrom,
    LocalDateTime effectiveTo
) {}


