package com.phtdev.webshopcatalog.service;

import com.phtdev.webshopcatalog.dto.CategoryDTO;
import com.phtdev.webshopcatalog.entities.Category;
import com.phtdev.webshopcatalog.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);
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

    @Transactional(readOnly = true)
    @Cacheable
    public CategoryDTO findById(Long id) {
        LOGGER.info("Cache miss: consultando banco para o id: {}", id);
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("qqq por enquanto")
        );

        return new CategoryDTO(category.getId(), category.getName());
    }


    @CacheEvict
    public void invalidate(Long id) {
        LOGGER.info("Cache invalidado para {}", id);
    }
}
