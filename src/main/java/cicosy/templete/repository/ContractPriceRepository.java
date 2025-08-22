package cicosy.templete.repository;

import cicosy.templete.domain.ContractPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractPriceRepository extends JpaRepository<ContractPrice, Long> {
    List<ContractPrice> findByItemId(Long itemId);
}


