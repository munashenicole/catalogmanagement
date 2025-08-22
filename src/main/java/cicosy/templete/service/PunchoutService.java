package cicosy.templete.service;

import cicosy.templete.domain.dto.CatalogItemDTO;
import cicosy.templete.domain.dto.PunchoutConfigDTO;

public interface PunchoutService {
    PunchoutConfigDTO configure(PunchoutConfigDTO dto);
    String getPunchoutRedirectUrl(Long catalogId);
    java.util.List<PunchoutConfigDTO> list();
}


