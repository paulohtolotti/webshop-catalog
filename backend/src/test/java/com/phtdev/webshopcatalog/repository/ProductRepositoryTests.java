package com.phtdev.webshopcatalog.repository;

import com.phtdev.webshopcatalog.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    @Test
    public void deleteShouldRemoveProductWhenIdExists() {
        // Arrange
        long existingID = 11L;

        // Act
        repository.deleteById(11L);

        var product = repository.findById(existingID);

        // Assert
        Assertions.assertTrue(product.isEmpty());
    }

    @Test
    public void existsByNameShouldReturnObjectIndependentlyOfCase() {
        // Arrange
        String name = "WatcHmeN";

        // Act
        Optional<Product> obj = repository.existsByName(name);

        // Assert
        Assertions.assertTrue(obj.isPresent());
    }

    @Test
    public void existsByNameShouldNotReturnObjectWhenNotRegistered() {
        // Arrange
        String name = "New product";

        // Act
        Optional<Product> obj = repository.existsByName(name);

        // Assert
        Assertions.assertFalse(obj.isPresent());
    }

}
