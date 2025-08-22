package cicosy.templete.controller;

import cicosy.templete.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoriesController {

    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    public String categoriesPage(Model model) {
        model.addAttribute("categories", categoryService.list());
        return "categories/index";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','PRICING')")
    public String create(@RequestParam String name,
                         @RequestParam(required = false) String description,
                         @RequestParam(required = false) Long parentId) {
        categoryService.create(name, description, parentId);
        return "redirect:/categories";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}
