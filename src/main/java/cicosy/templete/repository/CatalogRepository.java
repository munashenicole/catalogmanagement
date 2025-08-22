package cicosy.templete.repository;

import cicosy.templete.domain.Catalog;
import cicosy.templete.domain.CatalogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    List<Catalog> findByStatus(CatalogStatus status);

    List<Catalog> findAllByOrderByIdDesc();

    List<Catalog> findByStatusOrderByIdDesc(CatalogStatus status);

    boolean existsByCode(String code);

    Catalog findByCode(String code);
}