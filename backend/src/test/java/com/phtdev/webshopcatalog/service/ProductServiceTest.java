package com.phtdev.webshopcatalog.service;

import com.phtdev.webshopcatalog.dto.ProductDTO;
import com.phtdev.webshopcatalog.entities.Category;
import com.phtdev.webshopcatalog.entities.Product;
import com.phtdev.webshopcatalog.factory.Factory;
import com.phtdev.webshopcatalog.repository.CategoryRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private long validId;
    private long invalidId;
    private long dependentId;
    private long categoryId;
    private PageImpl<Product> page;
    private final Product product = Factory.createProduct();
    private final Category category = Factory.createCategory();
    private final ProductDTO dto = Factory.createProductDto();

    @BeforeEach
    void setUp() {
        validId = 1L;
        invalidId = 150L;
        dependentId = 5L;
        categoryId = 1L;
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
        Mockito.when(repository.findAllWithCategories(ArgumentMatchers.any())).thenReturn(page);

        var expected = service.findAll( ArgumentMatchers.any());

        Assertions.assertFalse(expected.isEmpty());

        Mockito.verify(repository, Mockito.times(1)).findAllWithCategories((Pageable)
                ArgumentMatchers.any());
    }

    @Test
    public void findByIdShouldReturnDtoWhenValidId() {
        // Arrange
        Mockito.when(repository.findById(validId)).thenReturn(Optional.of(product));
        // Act
        ProductDTO dto = service.findById(validId);

        // Assert
        Assertions.assertNotNull(dto);
        Mockito.verify(repository, Mockito.times(1)).findById(validId);
    }

    @Test
    public void findByIdShouldThrowWhenInvalidId() {
        // Arrange
        Mockito.when(repository.findById(invalidId)).thenReturn(Optional.empty());

        // Act + Assertion
        Assertions.assertThrows(ResourceNotRegisteredException.class,
                () -> service.findById(invalidId));

        Mockito.verify(repository, Mockito.times(1)).findById(invalidId);
    }

    @Test
    public void updateShouldThrowWhenInvalidId() {
        // Arrange
        Mockito.when(repository.existsById(invalidId)).thenReturn(false);

        // Act + assert
        Assertions.assertThrows(ResourceNotRegisteredException.class,
                () -> service.update(invalidId, dto));

        Mockito.verify(repository, Mockito.times(1)).existsById(invalidId);
    }

    @Test
    public void updateShouldReturnDtoWhenValidId() {
        // Arrange
        Mockito.when(repository.existsById(validId)).thenReturn(true);
        Mockito.when(repository.getReferenceById(validId)).thenReturn(product);
        Mockito.when(categoryRepository.getReferenceById(ArgumentMatchers.anyLong())).thenReturn(category);

        Mockito.when(repository.save(product)).thenReturn(product);

        ProductDTO requestDto = Factory.createProductDto();

        //Act
        ProductDTO dto = service.update(validId, requestDto);

        //Assert
        Assertions.assertNotNull(dto);
        Mockito.verify(repository, Mockito.times(1)).existsById(validId);
        Mockito.verify(repository, Mockito.times(1)).getReferenceById(validId);
        Mockito.verify(categoryRepository, Mockito.times(1)).getReferenceById(categoryId);
    }
}
