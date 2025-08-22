package cicosy.templete.service.impl;

import cicosy.templete.domain.Category;
import cicosy.templete.repository.CategoryRepository;
import cicosy.templete.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category create(String name, String description, Long parentId) {
        Category cat = new Category();
        cat.setName(name);
        cat.setDescription(description);
        if (parentId != null) {
            cat.setParent(categoryRepository.findById(parentId).orElse(null));
        }
        return categoryRepository.save(cat);
    }

    @Override
    public List<Category> list() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}


