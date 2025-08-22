package cicosy.templete.service.impl;

import cicosy.templete.repository.CatalogItemRepository;
import cicosy.templete.repository.CatalogRepository;
import cicosy.templete.repository.CategoryRepository;
import cicosy.templete.service.CatalogAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CatalogAnalyticsServiceImpl implements CatalogAnalyticsService {

    private final CatalogRepository catalogRepository;
    private final CatalogItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Map<String, Object> overview() {
        Map<String, Object> m = new HashMap<>();
        m.put("catalogCount", catalogRepository.count());
        m.put("itemCount", itemRepository.count());
        m.put("categoryCount", categoryRepository.count());
        m.put("preferredItemCount", itemRepository.countByPreferredTrue());
        return m;
    }
}


