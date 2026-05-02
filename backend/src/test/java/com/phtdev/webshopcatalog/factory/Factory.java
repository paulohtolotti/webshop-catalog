package com.phtdev.webshopcatalog.factory;

import com.phtdev.webshopcatalog.dto.ProductDTO;
import com.phtdev.webshopcatalog.entities.Category;
import com.phtdev.webshopcatalog.entities.Product;

import java.math.BigDecimal;

public class ProductFactory {

    public static Product createEmpty() {
        return new Product();
    }

    public static Product create() {
       Product product = new Product(null, "Swamp thing",
               "Allan's Moore revamp", new BigDecimal("25.00"), null);

        product.addCategory(new Category(1L, "books"));
        product.addCategory(new Category(2L, "comics"));
       return product;
    }

    public static ProductDTO createDto() {
        Product product = create();
        return ProductDTO.create(product);
    }
}
