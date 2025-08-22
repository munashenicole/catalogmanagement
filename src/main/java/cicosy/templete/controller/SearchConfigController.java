package cicosy.templete.controller;

import cicosy.templete.domain.SearchConfig;
import cicosy.templete.service.SearchConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchConfigController {

    private final SearchConfigService searchConfigService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    public String searchConfigPage(Model model) {
        model.addAttribute("cfg", searchConfigService.getOrCreate());
        return "search/index";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    public String save(@ModelAttribute SearchConfig cfg) {
        searchConfigService.save(cfg);
        return "redirect:/search";
    }
}


