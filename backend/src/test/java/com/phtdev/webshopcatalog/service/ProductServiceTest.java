package com.phtdev.webshopcatalog.service;

import com.phtdev.webshopcatalog.repository.ProductRepository;
import com.phtdev.webshopcatalog.service.exceptions.ResourceNotRegisteredException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long validId;
    private long invalidId;

    @BeforeEach
    void setUp() {
        validId = 1L;
        invalidId = 150L;

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
}
