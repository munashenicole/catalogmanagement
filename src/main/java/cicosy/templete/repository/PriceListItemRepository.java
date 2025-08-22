package cicosy.templete.repository;

import cicosy.templete.domain.PriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {
    Optional<PriceListItem> findByPriceListIdAndItemId(Long priceListId, Long itemId);
}


