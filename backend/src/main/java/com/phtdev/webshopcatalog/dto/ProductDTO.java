package com.phtdev.webshopcatalog.dto;

import com.phtdev.webshopcatalog.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ProductDTO(
        Long id,

        @NotBlank(message = "Product name can't be empty")
        @Size(min = 2, message = "Product name must have at least 2 characters")
        String name,

        @NotBlank(message = "Product description name can't be empty")
        @Size(min = 5, max = 100, message = "Description must have minimum of 2 characters and a maximum of 100 characters")
        String description,

        @Positive(message = "Price must be a positive")
        BigDecimal price,

        String imgUrl,

        @Size(min = 1, message = "Product must have at least one category associated")
        List<CategoryDTO> categories
) {
        public static ProductDTO create(Product entity) {
                return new ProductDTO(entity.getId(), entity.getName(),
                        entity.getDescription(), entity.getPrice(),
                        entity.getImgUrl(),
                        entity.getCategories().stream().map(CategoryDTO::create).toList()
                );
        }
}
