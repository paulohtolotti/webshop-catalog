package com.phtdev.webshopcatalog.repository;

import com.phtdev.webshopcatalog.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    Page<Category> findAll(Pageable pageable);

    Optional<Category> findById(Long id);

    @Query(value = "SELECT obj FROM Category obj WHERE UPPER(obj.name) = UPPER(:name)")
    Optional<Category> existsByName(String name);
}
