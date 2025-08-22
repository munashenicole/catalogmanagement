package cicosy.templete.service;

import org.springframework.web.multipart.MultipartFile;

public interface HostedCatalogService {
    void uploadHostedCatalog(Long catalogId, MultipartFile file, String format);
}


