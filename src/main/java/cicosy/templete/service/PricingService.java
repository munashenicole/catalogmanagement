package cicosy.templete.service;

import cicosy.templete.domain.dto.PriceDTO;

import java.math.BigDecimal;
import java.util.Optional;

public interface PricingService {
    void addToPriceList(Long priceListId, Long itemId, BigDecimal price);
    void createContractPrice(PriceDTO dto);
    Optional<BigDecimal> getEffectivePrice(Long itemId);
}


