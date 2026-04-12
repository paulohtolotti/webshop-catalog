package com.phtdev.webshopcatalog.service;

import com.phtdev.webshopcatalog.dto.ProductDTO;
import com.phtdev.webshopcatalog.entities.Product;
import com.phtdev.webshopcatalog.repository.ProductRepository;
import com.phtdev.webshopcatalog.service.exceptions.ResourceNotRegisteredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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


}
