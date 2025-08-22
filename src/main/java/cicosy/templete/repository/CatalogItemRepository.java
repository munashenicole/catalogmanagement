package cicosy.templete.repository;

import cicosy.templete.domain.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {
    List<CatalogItem> findByCatalogId(Long catalogId);
    List<CatalogItem> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(String name, String sku);
    long countByPreferredTrue();
}


