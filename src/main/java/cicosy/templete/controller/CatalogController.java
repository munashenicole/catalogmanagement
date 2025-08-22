package cicosy.templete.controller;

import cicosy.templete.domain.CatalogStatus;
import cicosy.templete.domain.dto.CatalogDTO;
import cicosy.templete.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalogs")
public class CatalogController {

    private final CatalogService catalogService;

    // Show all catalogs (active + draft)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PRICING','BUYER')")
    public String index(Model model) {
        List<CatalogDTO> catalogs = catalogService.list(null);
        model.addAttribute("catalogs", catalogs);
        model.addAttribute("hasSidebar", true); // Flag for sidebar styling
        return "catalog/catalogs"; // templates/catalog/catalogs.html
    }

    // View specific catalog details
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PRICING','BUYER')")
    public String viewCatalog(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<CatalogDTO> catalogOpt = catalogService.get(id);
        if (catalogOpt.isPresent()) {
            model.addAttribute("catalog", catalogOpt.get());
            model.addAttribute("hasSidebar", true);
            return "catalog/catalog-details"; // templates/catalog/catalog-details.html
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Catalog not found!");
            return "redirect:/catalogs";
        }
    }

    // Create new catalog
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createCatalog(
            @RequestParam String code,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "DRAFT") String status,
            RedirectAttributes redirectAttributes
    ) {
        try {
            // Validate inputs
            if (code == null || code.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Catalog code is required!");
                return "redirect:/catalogs";
            }

            if (name == null || name.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Catalog name is required!");
                return "redirect:/catalogs";
            }

            // Parse the status string to enum
            CatalogStatus catalogStatus = CatalogStatus.valueOf(status.toUpperCase());

            catalogService.create(new CatalogDTO(
                    null,
                    code.trim().toUpperCase(),
                    name.trim(),
                    description != null ? description.trim() : null,
                    catalogStatus,
                    null,
                    null
            ));

            redirectAttributes.addFlashAttribute("successMessage",
                    "Catalog '" + name.trim() + "' created successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error creating catalog: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Unexpected error occurred. Please try again.");
        }

        return "redirect:/catalogs";
    }

    // Update existing catalog
    @PostMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateCatalog(
            @PathVariable Long id,
            @RequestParam String code,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "DRAFT") String status,
            RedirectAttributes redirectAttributes
    ) {
        try {
            CatalogStatus catalogStatus = CatalogStatus.valueOf(status.toUpperCase());

            catalogService.update(id, new CatalogDTO(
                    id,
                    code.trim().toUpperCase(),
                    name.trim(),
                    description != null ? description.trim() : null,
                    catalogStatus,
                    null,
                    null
            ));

            redirectAttributes.addFlashAttribute("successMessage",
                    "Catalog '" + name.trim() + "' updated successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error updating catalog: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Unexpected error occurred. Please try again.");
        }

        return "redirect:/catalogs";
    }

    // Activate catalog
    @PostMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public String activate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            catalogService.activate(id);
            redirectAttributes.addFlashAttribute("successMessage", "Catalog activated successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error activating catalog: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unexpected error occurred. Please try again.");
        }
        return "redirect:/catalogs";
    }

    // Deactivate catalog
    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public String deactivate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            catalogService.deactivate(id);
            redirectAttributes.addFlashAttribute("successMessage", "Catalog deactivated successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deactivating catalog: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unexpected error occurred. Please try again.");
        }
        return "redirect:/catalogs";
    }

    // API endpoint (returns JSON)
    @GetMapping("/api")
    @ResponseBody
    public List<CatalogDTO> list(@RequestParam(required = false) CatalogStatus status) {
        return catalogService.list(status);
    }

    // API endpoint for single catalog
    @GetMapping("/api/{id}")
    @ResponseBody
    public Optional<CatalogDTO> getCatalog(@PathVariable Long id) {
        return catalogService.get(id);
    }
}