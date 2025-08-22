package cicosy.templete.controller;

import cicosy.templete.domain.dto.CatalogItemDTO;
import cicosy.templete.service.ItemManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class ItemController {

    private final ItemManagementService itemService;

    @GetMapping("/items")
    public String itemsPage() {
        return "items/index";
    }

    @GetMapping("/items/search")
    @ResponseBody
    public List<CatalogItemDTO> search(@RequestParam String q) {
        return itemService.search(q);
    }

    @GetMapping("/items/preferred")
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    public String preferredItemsPage() {
        return "items/preferred";
    }

    @PostMapping("/items")
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    @ResponseBody
    public CatalogItemDTO add(@RequestBody CatalogItemDTO dto) {
        return itemService.add(dto);
    }

    @PostMapping("/items/{id}/preferred")
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    @ResponseBody
    public void setPreferred(@PathVariable Long id, @RequestParam boolean preferred) {
        itemService.setPreferred(id, preferred);
    }
}
