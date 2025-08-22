package cicosy.templete.service.impl;

import cicosy.templete.domain.ContractPrice;
import cicosy.templete.domain.PriceList;
import cicosy.templete.domain.PriceListItem;
import cicosy.templete.domain.dto.PriceDTO;
import cicosy.templete.repository.CatalogItemRepository;
import cicosy.templete.repository.ContractPriceRepository;
import cicosy.templete.repository.PriceListItemRepository;
import cicosy.templete.repository.PriceListRepository;
import cicosy.templete.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {

    private final PriceListRepository priceListRepository;
    private final PriceListItemRepository priceListItemRepository;
    private final ContractPriceRepository contractPriceRepository;
    private final CatalogItemRepository itemRepository;

    @Override
    public void addToPriceList(Long priceListId, Long itemId, BigDecimal price) {
        PriceList priceList = priceListRepository.findById(priceListId).orElseThrow();
        var item = itemRepository.findById(itemId).orElseThrow();
        PriceListItem pli = priceListItemRepository
            .findByPriceListIdAndItemId(priceListId, itemId)
            .orElse(new PriceListItem());
        pli.setPriceList(priceList);
        pli.setItem(item);
        pli.setPrice(price);
        priceListItemRepository.save(pli);
    }

    @Override
    public void createContractPrice(PriceDTO dto) {
        var item = itemRepository.findById(dto.itemId()).orElseThrow();
        ContractPrice cp = new ContractPrice();
        cp.setItem(item);
        cp.setPrice(dto.price());
        cp.setContractId(dto.contractId());
        cp.setSupplierName(dto.supplierName());
        cp.setStartDate(dto.startDate());
        cp.setEndDate(dto.endDate());
        contractPriceRepository.save(cp);
    }

    @Override
    public Optional<BigDecimal> getEffectivePrice(Long itemId) {
        LocalDate today = LocalDate.now();

        var cps = contractPriceRepository.findByItemId(itemId);
        var activeCp = cps.stream().filter(cp ->
            (cp.getStartDate() == null || !today.isBefore(cp.getStartDate())) &&
            (cp.getEndDate() == null || !today.isAfter(cp.getEndDate()))
        ).min(Comparator.comparing(ContractPrice::getStartDate));
        if (activeCp.isPresent()) {
            return Optional.ofNullable(activeCp.get().getPrice());
        }

        var item = itemRepository.findById(itemId).orElseThrow();
        var pls = priceListRepository.findByCatalogIdOrderByStartDateDesc(item.getCatalog().getId());
        for (PriceList pl : pls) {
            if ((pl.getStartDate() == null || !today.isBefore(pl.getStartDate())) &&
                (pl.getEndDate() == null || !today.isAfter(pl.getEndDate()))) {
                return priceListItemRepository
                    .findByPriceListIdAndItemId(pl.getId(), itemId)
                    .map(PriceListItem::getPrice);
            }
        }
        return Optional.empty();
    }
}


