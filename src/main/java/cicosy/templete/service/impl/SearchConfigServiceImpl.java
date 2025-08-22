package cicosy.templete.service.impl;

import cicosy.templete.domain.SearchConfig;
import cicosy.templete.repository.SearchConfigRepository;
import cicosy.templete.service.SearchConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchConfigServiceImpl implements SearchConfigService {

    private final SearchConfigRepository repository;

    @Override
    public SearchConfig getOrCreate() {
        return repository.findAll().stream().findFirst().orElseGet(() -> repository.save(new SearchConfig()));
    }

    @Override
    public SearchConfig save(SearchConfig cfg) {
        return repository.save(cfg);
    }
}


