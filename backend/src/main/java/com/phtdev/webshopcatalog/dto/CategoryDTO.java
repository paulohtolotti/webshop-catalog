package com.phtdev.webshopcatalog.dto;

import com.phtdev.webshopcatalog.entities.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record CategoryDTO(
        Long id,

        @NotBlank(message = "Category name can't be empty")
        @Size(min=2, message = "Category must have at least two characters")
        String name) implements Serializable {

        public static CategoryDTO create(Category entity) {
                return new CategoryDTO(entity.getId(), entity.getName());
        }
}
