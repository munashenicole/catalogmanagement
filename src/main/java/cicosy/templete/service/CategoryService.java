package cicosy.templete.service;

import cicosy.templete.domain.Category;

import java.util.List;

public interface CategoryService {
    Category create(String name, String description, Long parentId);
    List<Category> list();
    void delete(Long id);
}


