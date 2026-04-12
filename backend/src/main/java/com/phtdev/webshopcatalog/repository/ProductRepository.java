package com.phtdev.webshopcatalog.repository;

import com.phtdev.webshopcatalog.dto.ProductDTO;
import com.phtdev.webshopcatalog.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Consulta de produtos com categorias associadas.
     * A consulta manual evita o problema N+1 que o método findAll gera.
     * @param pageable
     * @return página com todos os produtos e categorias.
     */
    @Query(value = "SELECT obj FROM Product obj JOIN FETCH obj.categories")
    public Page<Product> findAllWithCategories(Pageable pageable);
}
