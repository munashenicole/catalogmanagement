package cicosy.templete.service;

import cicosy.templete.domain.CatalogStatus;
import cicosy.templete.domain.dto.CatalogDTO;

import java.util.List;
import java.util.Optional;

public interface CatalogService {

    /**
     * Create a new catalog
     * @param dto Catalog data
     * @return Created catalog
     * @throws IllegalArgumentException if validation fails
     */
    CatalogDTO create(CatalogDTO dto);

    /**
     * Get catalog by ID
     * @param id Catalog ID
     * @return Catalog if found
     */
    Optional<CatalogDTO> get(Long id);

    /**
     * List catalogs by status
     * @param status Filter by status (null for all)
     * @return List of catalogs
     */
    List<CatalogDTO> list(CatalogStatus status);

    /**
     * Update existing catalog
     * @param id Catalog ID
     * @param dto Updated data
     * @return Updated catalog
     * @throws IllegalArgumentException if catalog not found
     */
    CatalogDTO update(Long id, CatalogDTO dto);

    /**
     * Activate a catalog
     * @param id Catalog ID
     * @throws IllegalArgumentException if catalog not found or cannot be activated
     */
    void activate(Long id);

    /**
     * Deactivate a catalog
     * @param id Catalog ID
     * @throws IllegalArgumentException if catalog not found or cannot be deactivated
     */
    void deactivate(Long id);

    /**
     * Check if catalog code exists
     * @param code Catalog code
     * @return true if exists
     */
    default boolean existsByCode(String code) {
        // Default implementation - override if needed
        return false;
    }
}