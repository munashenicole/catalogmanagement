package cicosy.templete.service.impl;

import cicosy.templete.domain.Catalog;
import cicosy.templete.domain.PunchoutConfig;
import cicosy.templete.domain.dto.PunchoutConfigDTO;
import cicosy.templete.repository.CatalogRepository;
import cicosy.templete.repository.PunchoutConfigRepository;
import cicosy.templete.service.PunchoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class PunchoutServiceImpl implements PunchoutService {

    private final PunchoutConfigRepository punchoutConfigRepository;
    private final CatalogRepository catalogRepository;

    @Override
    @Transactional
    public PunchoutConfigDTO configure(PunchoutConfigDTO dto) {
        Catalog catalog = catalogRepository.findById(dto.catalogId()).orElseThrow();
        PunchoutConfig cfg = punchoutConfigRepository.findByCatalogId(dto.catalogId()).orElse(new PunchoutConfig());
        cfg.setCatalog(catalog);
        cfg.setEndpointUrl(dto.endpointUrl());
        cfg.setIdentity(dto.identity());
        cfg.setSharedSecret(dto.sharedSecret());
        cfg.setProtocol(dto.protocol());
        cfg.setEnabled(Boolean.TRUE.equals(dto.enabled()));
        cfg = punchoutConfigRepository.save(cfg);
        return new PunchoutConfigDTO(
            cfg.getCatalog().getId(),
            cfg.getEndpointUrl(),
            cfg.getIdentity(),
            cfg.getSharedSecret(),
            cfg.getProtocol(),
            cfg.getEnabled()
        );
    }

    @Override
    public String getPunchoutRedirectUrl(Long catalogId) {
        PunchoutConfig cfg = punchoutConfigRepository.findByCatalogId(catalogId).orElseThrow();
        return UriComponentsBuilder.fromUriString(cfg.getEndpointUrl())
            .queryParam("catalogId", catalogId)
            .toUriString();
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<PunchoutConfigDTO> list() {
        return punchoutConfigRepository.findAll().stream()
            .map(cfg -> new PunchoutConfigDTO(
                cfg.getCatalog() != null ? cfg.getCatalog().getId() : null,
                cfg.getEndpointUrl(),
                cfg.getIdentity(),
                cfg.getSharedSecret(),
                cfg.getProtocol(),
                cfg.getEnabled()
            ))
            .toList();
    }
}
