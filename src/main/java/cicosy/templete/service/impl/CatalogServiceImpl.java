package cicosy.templete.service.impl;

import cicosy.templete.domain.Catalog;
import cicosy.templete.domain.CatalogStatus;
import cicosy.templete.domain.dto.CatalogDTO;
import cicosy.templete.repository.CatalogRepository;
import cicosy.templete.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Override
    public CatalogDTO create(CatalogDTO dto) {
        // Validate required fields
        if (dto.code() == null || dto.code().trim().isEmpty()) {
            throw new IllegalArgumentException("Catalog code is required");
        }
        if (dto.name() == null || dto.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Catalog name is required");
        }

        // Check if code already exists
        if (catalogRepository.existsByCode(dto.code())) {
            throw new IllegalArgumentException("Catalog with code '" + dto.code() + "' already exists");
        }

        Catalog c = new Catalog();
        c.setCode(dto.code().toUpperCase().trim()); // Ensure uppercase and trimmed
        c.setName(dto.name().trim());
        c.setDescription(dto.description() != null ? dto.description().trim() : null);
        c.setStatus(dto.status() != null ? dto.status() : CatalogStatus.DRAFT);

        // Set effective dates based on status
        if (c.getStatus() == CatalogStatus.ACTIVE) {
            c.setEffectiveFrom(LocalDateTime.now());
        } else {
            c.setEffectiveFrom(dto.effectiveFrom());
        }
        c.setEffectiveTo(dto.effectiveTo());

        c = catalogRepository.save(c);
        return mapToDTO(c);
    }

    @Override
    public Optional<CatalogDTO> get(Long id) {
        return catalogRepository.findById(id).map(this::mapToDTO);
    }

    @Override
    public List<CatalogDTO> list(CatalogStatus status) {
        var list = (status == null) ?
                catalogRepository.findAllByOrderByIdDesc() :
                catalogRepository.findByStatusOrderByIdDesc(status);
        return list.stream().map(this::mapToDTO).toList();
    }

    @Override
    public CatalogDTO update(Long id, CatalogDTO dto) {
        Catalog c = catalogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Catalog not found with id: " + id));

        c.setName(dto.name().trim());
        c.setDescription(dto.description() != null ? dto.description().trim() : null);
        c.setStatus(dto.status());
        c.setEffectiveFrom(dto.effectiveFrom());
        c.setEffectiveTo(dto.effectiveTo());

        c = catalogRepository.save(c);
        return mapToDTO(c);
    }

    @Override
    public void activate(Long id) {
        Catalog c = catalogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Catalog not found with id: " + id));

        c.setStatus(CatalogStatus.ACTIVE);
        if (c.getEffectiveFrom() == null) {
            c.setEffectiveFrom(LocalDateTime.now());
        }
        catalogRepository.save(c);
    }

    @Override
    public void deactivate(Long id) {
        Catalog c = catalogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Catalog not found with id: " + id));

        c.setStatus(CatalogStatus.INACTIVE);
        catalogRepository.save(c);
    }

    private CatalogDTO mapToDTO(Catalog c) {
        return new CatalogDTO(
                c.getId(),
                c.getCode(),
                c.getName(),
                c.getDescription(),
                c.getStatus(),
                c.getEffectiveFrom(),
                c.getEffectiveTo()
        );
    }
}