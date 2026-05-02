package com.phtdev.webshopcatalog.repository;

import com.phtdev.webshopcatalog.entities.Product;
import com.phtdev.webshopcatalog.factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;
    private long existingID;
    private String name;

    @BeforeEach
    void setUp() {
        existingID = 11L;
        name = "WatcHmeN";
    }

    @BeforeAll
    static void beforeAll() {

    }

    @Test
    public void deleteShouldRemoveProductWhenIdExists() {

        // Act
        repository.deleteById(11L);

        var product = repository.findById(existingID);

        // Assert
        Assertions.assertTrue(product.isEmpty());
    }

    @Test
    public void existsByNameShouldReturnObjectIndependentlyOfCase() {
        // Arrange


        // Act
        Optional<Product> obj = repository.existsByName(name);

        // Assert
        Assertions.assertTrue(obj.isPresent());
    }

    @Test
    public void existsByNameShouldNotReturnObjectWhenNotRegistered() {
        // Arrange
        String nonExistingName = "New product";

        // Act
        Optional<Product> obj = repository.existsByName(nonExistingName);

        // Assert
        Assertions.assertFalse(obj.isPresent());
    }

    @Test
    public void saveShouldCreateAutoIncrementedID() {
        long expectedId = 12;
        Product entity = Factory.createProduct();

        entity = repository.save(entity);

        Assertions.assertEquals(expectedId, entity.getId());
    }

}
