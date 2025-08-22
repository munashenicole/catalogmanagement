package cicosy.templete.repository;

import cicosy.templete.domain.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceListRepository extends JpaRepository<PriceList, Long> {
    List<PriceList> findByCatalogIdOrderByStartDateDesc(Long catalogId);
}


