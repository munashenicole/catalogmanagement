package cicosy.templete.domain.dto;

import java.math.BigDecimal;
import java.util.Set;

public record CatalogItemDTO(
    Long id,
    Long catalogId,
    String sku,
    String name,
    String description,
    String unitOfMeasure,
    BigDecimal listPrice,
    Boolean preferred,
    Set<Long> categoryIds
) {}


