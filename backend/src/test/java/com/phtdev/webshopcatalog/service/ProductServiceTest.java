package com.phtdev.webshopcatalog.service;

import com.phtdev.webshopcatalog.dto.ProductDTO;
import com.phtdev.webshopcatalog.entities.Product;
import com.phtdev.webshopcatalog.factory.Factory;
import com.phtdev.webshopcatalog.repository.ProductRepository;
import com.phtdev.webshopcatalog.service.exceptions.DatabaseViolationOccuredException;
import com.phtdev.webshopcatalog.service.exceptions.ResourceNotRegisteredException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long validId;
    private long invalidId;
    private long dependentId;
    private PageImpl<Product> page;
    private Product product = Factory.createProduct();

    @BeforeEach
    void setUp() {
        validId = 1L;
        invalidId = 150L;
        dependentId = 5L;
        page = new PageImpl<>(List.of(product));
        //Caso usasse o Spring Extensions com @MockitoBean MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deleteShouldDoNothingWhenExistingId() {
        // Arrange
        Mockito.when(repository.existsById(validId)).thenReturn(true);
        Mockito.doNothing().when(repository).deleteById(validId);

        // Act + Assert
        Assertions.assertDoesNotThrow(() ->  service.delete(validId));

        // Assert se o método mockado do Mockito foi usado
        Mockito.verify(repository, Mockito.times(1)).existsById(validId);
        Mockito.verify(repository, Mockito.times(1)).deleteById(validId);

    }

    @Test
    public void deleteShouldThrowWhenInexistingId() {
        // Arrange
        Mockito.when(repository.existsById(invalidId)).thenReturn(false);
        // Act + Assert
        Assertions.assertThrows(ResourceNotRegisteredException.class,
                () -> service.delete(invalidId)
        );

        Mockito.verify(repository, Mockito.times(1)).existsById(invalidId);
        Mockito.verify(repository, Mockito.never()).deleteById(invalidId);
    }

    @Test
    public void deleteShouldThrowExceptionWhenDependentId() {
        // Arrange
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        // Act + Assert
        Assertions.assertThrows(DatabaseViolationOccuredException.class,
                () -> service.delete(dependentId)
        );
        Mockito.verify(repository, Mockito.times(1)).existsById(dependentId);
        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void findAllShouldReturnPage() {
        Mockito.when(repository.findAllWithCategories((Pageable) ArgumentMatchers.any())).thenReturn(page);

        var expected = service.findAll((Pageable) ArgumentMatchers.any());

        Assertions.assertFalse(expected.isEmpty());

        Mockito.verify(repository, Mockito.times(1)).findAllWithCategories((Pageable)
                ArgumentMatchers.any());
    }

}
