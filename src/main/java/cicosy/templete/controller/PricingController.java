package cicosy.templete.controller;

import cicosy.templete.domain.dto.PriceDTO;
import cicosy.templete.service.PricingService;
import cicosy.templete.service.PriceListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class PricingController {

    private final PricingService pricingService;
    private final PriceListService priceListService;

    @GetMapping("/pricing")
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    public String pricingPage(@RequestParam(required = false) Long catalogId, org.springframework.ui.Model model) {
        if (catalogId != null) {
            model.addAttribute("priceLists", priceListService.listByCatalog(catalogId));
        }
        return "pricing/index";
    }

    @GetMapping("/pricing/contract")
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    public String contractPage() {
        return "pricing/contract";
    }

    @PostMapping("/pricing/contract")
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    @ResponseBody
    public void contract(@RequestBody PriceDTO dto) {
        pricingService.createContractPrice(dto);
    }

    @PostMapping("/pricing/price-list")
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    public String createPriceList(@RequestParam Long catalogId,
                                  @RequestParam String name,
                                  @RequestParam String currency,
                                  @RequestParam(required = false) java.time.LocalDate startDate,
                                  @RequestParam(required = false) java.time.LocalDate endDate) {
        priceListService.create(catalogId, name, currency, startDate, endDate);
        return "redirect:/pricing?catalogId=" + catalogId;
    }

    @GetMapping("/pricing/effective/{itemId}")
    @ResponseBody
    public Optional<BigDecimal> effective(@PathVariable Long itemId) {
        return pricingService.getEffectivePrice(itemId);
    }
}
