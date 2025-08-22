
package cicosy.templete.controller;

import cicosy.templete.domain.dto.PunchoutConfigDTO;
import cicosy.templete.service.PunchoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/punchout")
public class PunchoutController {

    private final PunchoutService punchoutService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String punchoutPage(Model model) {
        model.addAttribute("configs", punchoutService.list());
        return "punchout/index";
    }

    @PostMapping("/config")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public PunchoutConfigDTO configure(@RequestBody PunchoutConfigDTO dto) {
        return punchoutService.configure(dto);
    }

    @GetMapping("/redirect/{catalogId}")
    public String redirect(@PathVariable Long catalogId) {
        String url = punchoutService.getPunchoutRedirectUrl(catalogId);
        return "redirect:" + url;
    }
}

