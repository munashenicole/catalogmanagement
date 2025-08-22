package cicosy.templete.controller;

import cicosy.templete.repository.CatalogRepository;
import cicosy.templete.service.HostedCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hosted")
public class HostedCatalogController {

    private final HostedCatalogService hostedCatalogService;
    private final CatalogRepository catalogRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    public String hostedPage(Model model) {
        model.addAttribute("catalogs", catalogRepository.findAll());
        return "hosted/index";
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    public String upload(@RequestParam Long catalogId,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam(defaultValue = "CSV") String format) {
        hostedCatalogService.uploadHostedCatalog(catalogId, file, format);
        return "redirect:/hosted";
    }
}


