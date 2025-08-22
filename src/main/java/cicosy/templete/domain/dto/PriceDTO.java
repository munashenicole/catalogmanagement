package cicosy.templete.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PriceDTO(
    Long itemId,
    BigDecimal price,
    String currency,
    String contractId,
    String supplierName,
    LocalDate startDate,
    LocalDate endDate
) {}


