package com.phtdev.webshopcatalog.service;

import com.phtdev.webshopcatalog.dto.CategoryDTO;
import com.phtdev.webshopcatalog.entities.Category;
import com.phtdev.webshopcatalog.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);

        return categoriesPage.map(
                p -> new CategoryDTO(p.getId(), p.getName())
        );
    }
}
