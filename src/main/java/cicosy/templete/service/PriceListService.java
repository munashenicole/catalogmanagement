package cicosy.templete.service;

import cicosy.templete.domain.PriceList;

import java.time.LocalDate;
import java.util.List;

public interface PriceListService {
    PriceList create(Long catalogId, String name, String currency, LocalDate startDate, LocalDate endDate);
    List<PriceList> listByCatalog(Long catalogId);
}


