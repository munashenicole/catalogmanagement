package cicosy.templete.controller;

import cicosy.templete.service.CatalogAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AnalyticsController {

    private final CatalogAnalyticsService analyticsService;

    @GetMapping("/analytics")
    public String analytics(Model model) {
        model.addAttribute("metrics", analyticsService.overview());
        return "analytics/index";
    }

    @GetMapping("/api/analytics/overview")
    @ResponseBody
    public Map<String, Object> overview() {
        return analyticsService.overview();
    }
}


