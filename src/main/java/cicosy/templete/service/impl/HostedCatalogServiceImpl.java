package cicosy.templete.service.impl;

import cicosy.templete.domain.Catalog;
import cicosy.templete.domain.HostedCatalog;
import cicosy.templete.repository.CatalogRepository;
import cicosy.templete.repository.HostedCatalogRepository;
import cicosy.templete.service.HostedCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class HostedCatalogServiceImpl implements HostedCatalogService {

    private final HostedCatalogRepository hostedCatalogRepository;
    private final CatalogRepository catalogRepository;

    @Override
    public void uploadHostedCatalog(Long catalogId, MultipartFile file, String format) {
        try {
            Catalog catalog = catalogRepository.findById(catalogId).orElseThrow();
            Path storageDir = Path.of("data", "hosted");
            Files.createDirectories(storageDir);
            String sanitized = file.getOriginalFilename() == null ? ("catalog-" + catalogId) : file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
            Path target = storageDir.resolve(catalog.getCode() + "-" + sanitized);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            HostedCatalog hc = hostedCatalogRepository.findAll().stream()
                .filter(h -> h.getCatalog() != null && h.getCatalog().getId().equals(catalogId))
                .findFirst()
                .orElse(new HostedCatalog());
            hc.setCatalog(catalog);
            hc.setFileLocation(target.toString());
            hc.setFormat(format);
            hostedCatalogRepository.save(hc);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store hosted catalog file", e);
        }
    }
}


