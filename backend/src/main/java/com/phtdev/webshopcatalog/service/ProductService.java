package com.phtdev.webshopcatalog.service;

import com.phtdev.webshopcatalog.dto.ProductDTO;
import com.phtdev.webshopcatalog.entities.Category;
import com.phtdev.webshopcatalog.entities.Product;
import com.phtdev.webshopcatalog.repository.CategoryRepository;
import com.phtdev.webshopcatalog.repository.ProductRepository;
import com.phtdev.webshopcatalog.service.exceptions.DatabaseViolationOccuredException;
import com.phtdev.webshopcatalog.service.exceptions.ResourceDuplicatedException;
import com.phtdev.webshopcatalog.service.exceptions.ResourceNotRegisteredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAllWithCategories(pageable).map(ProductDTO::create);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) throws ResourceNotRegisteredException {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotRegisteredException("Product " + id + " not registered")
        );

        LOGGER.info("Product {} found", id);
        return ProductDTO.create(product);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {

        boolean entityPresent = productRepository.existsByName(dto.name()).isPresent();

        if(entityPresent) {
            LOGGER.info("{} already registered. Throwing exception", dto.name());
            throw new ResourceDuplicatedException(dto.name()  + " already registered");
        }

        LOGGER.info("{} not registered. Saving it now.",dto.name());

        Product entity = new Product(dto);
        entity = productRepository.save(entity);
        return  ProductDTO.create(entity);
    }
    
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {

       if(!productRepository.existsById(id)) {
           throw new ResourceNotRegisteredException("Product" + id + " not registered");
       }

       Product entity = productRepository.getReferenceById(id);
       copyDtoToEntity(entity, dto);
       entity = productRepository.save(entity);
       return ProductDTO.create(entity);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        LOGGER.info("Deleting id {}", id);

        if(!productRepository.existsById(id)) {
            LOGGER.info("{} id not registered, throwing exception", id);
            throw new ResourceNotRegisteredException("Product " + id + " not registered");
        }

        try {
            productRepository.deleteById(id);
        } catch(DataIntegrityViolationException err) {
            throw new DatabaseViolationOccuredException("Integrity violation for id " + id);
        }
    }

    /**
     * Copia os dados de um DTO para a entidade, validando se algum campo do DTO é null antes de atribuir
     * @param entity
     * @param dto
     */
    private void copyDtoToEntity(Product entity, ProductDTO dto) {

        LOGGER.info("Copying DTO to entity for UPDATE");

        String name = dto.name() == null ? entity.getName() : dto.name();
        String description = dto.description() == null ? entity.getDescription() : dto.description();
        BigDecimal price = dto.price() == null ? entity.getPrice() : dto.price();
        String imgUrl = dto.imgUrl() == null ? entity.getImgUrl() : dto.imgUrl();

        if(dto.categories() != null) {
            entity.clear();
            dto.categories().forEach(
                    c -> {
                        Category category = categoryRepository.getReferenceById(c.id());
                        entity.addCategory(category);
                    }
            );
        }

        entity.setName(name);
        entity.setDescription(description);
        entity.setPrice(price);
        entity.setImgUrl(imgUrl);
    }
}
