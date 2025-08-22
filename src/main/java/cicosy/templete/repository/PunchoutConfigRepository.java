package cicosy.templete.repository;

import cicosy.templete.domain.PunchoutConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PunchoutConfigRepository extends JpaRepository<PunchoutConfig, Long> {
    Optional<PunchoutConfig> findByCatalogId(Long catalogId);
}


