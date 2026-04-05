package com.phtdev.webshopcatalog.service;

import com.phtdev.webshopcatalog.dto.CategoryDTO;
import com.phtdev.webshopcatalog.entities.Category;
import com.phtdev.webshopcatalog.repository.CategoryRepository;
import com.phtdev.webshopcatalog.service.exceptions.ResourceDuplicatedException;
import com.phtdev.webshopcatalog.service.exceptions.ResourceNotRegistered;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.RedisSubscribedConnectionException;
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
        LOGGER.info("Cache miss: querying for id: {}", id);
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotRegistered("Category " + id + " not registered")
        );

        return new CategoryDTO(category.getId(), category.getName());
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();

        boolean entityPresent = categoryRepository.existsByName(dto.name()).isPresent();

        if(entityPresent) {
            LOGGER.info("{} already registered. Throwing exception", dto.name());
            throw new ResourceDuplicatedException(dto.name()  + " already registered");
        }

        LOGGER.info("{} not registered. Saving it now.",dto.name());
        entity.setName(dto.name().toLowerCase());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity.getId(), entity.getName());
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {

        try {
            Category entity = categoryRepository.getReferenceById(id);

            entity.setName(dto.name());
            entity = categoryRepository.save(entity);

            return new CategoryDTO(entity.getId(), entity.getName());
        } catch(EntityNotFoundException err) {
            throw new ResourceNotRegistered("Cateogry " + id + " not registered");
        }


    }

    @CacheEvict
    public void invalidate(Long id) {
        LOGGER.info("Invalidated cache for id {}", id);
    }
}
