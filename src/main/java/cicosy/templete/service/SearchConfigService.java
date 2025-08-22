package cicosy.templete.service;

import cicosy.templete.domain.SearchConfig;

public interface SearchConfigService {
    SearchConfig getOrCreate();
    SearchConfig save(SearchConfig cfg);
}


