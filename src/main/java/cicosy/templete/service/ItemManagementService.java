package cicosy.templete.service;

import cicosy.templete.domain.dto.CatalogItemDTO;

import java.util.List;

public interface ItemManagementService {
    CatalogItemDTO add(CatalogItemDTO dto);
    CatalogItemDTO update(Long id, CatalogItemDTO dto);
    List<CatalogItemDTO> search(String query);
    void setPreferred(Long itemId, boolean preferred);
}


