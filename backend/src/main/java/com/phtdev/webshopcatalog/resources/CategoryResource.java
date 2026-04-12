package com.phtdev.webshopcatalog.resources;

import com.phtdev.webshopcatalog.dto.CategoryDTO;
import com.phtdev.webshopcatalog.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @PostMapping
    public ResponseEntity<CategoryDTO> insert(@RequestBody @Valid CategoryDTO dto) {
        logger.info("Creating a new entity");
        CategoryDTO dtoWithId = categoryService.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dtoWithId);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody @Valid CategoryDTO dto) {
        CategoryDTO updatedDto = categoryService.update(id, dto);

        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
