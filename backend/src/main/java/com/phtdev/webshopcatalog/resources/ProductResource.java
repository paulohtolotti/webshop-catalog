package com.phtdev.webshopcatalog.resources;

import com.phtdev.webshopcatalog.dto.ProductDTO;
import com.phtdev.webshopcatalog.service.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductResource.class);

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        LOGGER.info("Searching all products");
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody @Valid ProductDTO dto) {
        LOGGER.info("Creating a new entity");
        ProductDTO dtoWithId = productService.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dtoWithId);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        ProductDTO updatedDto = productService.update(id, dto);

        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
