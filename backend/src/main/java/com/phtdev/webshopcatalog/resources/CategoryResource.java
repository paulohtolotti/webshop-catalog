package com.phtdev.webshopcatalog.resources;

import com.phtdev.webshopcatalog.dto.CategoryDTO;
import com.phtdev.webshopcatalog.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    private static final Logger logger = LoggerFactory.getLogger(CategoryResource.class);

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById( @PathVariable Long id) {
        logger.info("Searching for category  {}", id);
        return ResponseEntity.ok(categoryService.findById(id));
    }
}
