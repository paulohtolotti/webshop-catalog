package com.phtdev.webshopcatalog.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository repository;

    @Test
    public void deleteShouldThrowWhenCategoryHaveProductAssociated() {
        // Arrange
        long categoryId = 1L;

        // Act & Assert
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> {
                    repository.deleteById(categoryId);
                    repository.flush();
                }
        );
    }
}
