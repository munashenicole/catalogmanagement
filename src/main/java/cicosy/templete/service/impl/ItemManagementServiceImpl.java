package cicosy.templete.service.impl;

import cicosy.templete.domain.Catalog;
import cicosy.templete.domain.CatalogItem;
import cicosy.templete.domain.Category;
import cicosy.templete.domain.dto.CatalogItemDTO;
import cicosy.templete.repository.CatalogItemRepository;
import cicosy.templete.repository.CatalogRepository;
import cicosy.templete.repository.CategoryRepository;
import cicosy.templete.service.ItemManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemManagementServiceImpl implements ItemManagementService {

    private final CatalogItemRepository itemRepository;
    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public CatalogItemDTO add(CatalogItemDTO dto) {
        Catalog catalog = catalogRepository.findById(dto.catalogId()).orElseThrow();
        CatalogItem item = new CatalogItem();
        item.setCatalog(catalog);
        item.setSku(dto.sku());
        item.setName(dto.name());
        item.setDescription(dto.description());
        item.setUnitOfMeasure(dto.unitOfMeasure());
        item.setListPrice(dto.listPrice());
        item.setPreferred(Boolean.TRUE.equals(dto.preferred()));
        if (dto.categoryIds() != null && !dto.categoryIds().isEmpty()) {
            item.setCategories(new HashSet<>(categoryRepository.findAllById(dto.categoryIds())));
        }
        item = itemRepository.save(item);
        return map(item);
    }

    @Override
    public CatalogItemDTO update(Long id, CatalogItemDTO dto) {
        CatalogItem item = itemRepository.findById(id).orElseThrow();
        item.setSku(dto.sku());
        item.setName(dto.name());
        item.setDescription(dto.description());
        item.setUnitOfMeasure(dto.unitOfMeasure());
        item.setListPrice(dto.listPrice());
        item.setPreferred(Boolean.TRUE.equals(dto.preferred()));
        if (dto.categoryIds() != null) {
            item.setCategories(new HashSet<>(categoryRepository.findAllById(dto.categoryIds())));
        }
        item = itemRepository.save(item);
        return map(item);
    }

    @Override
    public List<CatalogItemDTO> search(String query) {
        return itemRepository
            .findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(query, query)
            .stream().map(this::map).toList();
    }

    @Override
    public void setPreferred(Long itemId, boolean preferred) {
        CatalogItem item = itemRepository.findById(itemId).orElseThrow();
        item.setPreferred(preferred);
        itemRepository.save(item);
    }

    private CatalogItemDTO map(CatalogItem i) {
        return new CatalogItemDTO(
            i.getId(),
            i.getCatalog() != null ? i.getCatalog().getId() : null,
            i.getSku(),
            i.getName(),
            i.getDescription(),
            i.getUnitOfMeasure(),
            i.getListPrice(),
            i.getPreferred(),
            i.getCategories() == null ? null : i.getCategories().stream().map(Category::getId).collect(java.util.stream.Collectors.toSet())
        );
    }
}


