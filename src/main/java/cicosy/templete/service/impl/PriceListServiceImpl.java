package cicosy.templete.service.impl;

import cicosy.templete.domain.Catalog;
import cicosy.templete.domain.PriceList;
import cicosy.templete.repository.CatalogRepository;
import cicosy.templete.repository.PriceListRepository;
import cicosy.templete.service.PriceListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceListServiceImpl implements PriceListService {
    private final PriceListRepository priceListRepository;
    private final CatalogRepository catalogRepository;

    @Override
    public PriceList create(Long catalogId, String name, String currency, LocalDate startDate, LocalDate endDate) {
        Catalog catalog = catalogRepository.findById(catalogId).orElseThrow();
        PriceList pl = new PriceList();
        pl.setCatalog(catalog);
        pl.setName(name);
        pl.setCurrency(currency);
        pl.setStartDate(startDate);
        pl.setEndDate(endDate);
        return priceListRepository.save(pl);
    }

    @Override
    public List<PriceList> listByCatalog(Long catalogId) {
        return priceListRepository.findByCatalogIdOrderByStartDateDesc(catalogId);
    }
}


