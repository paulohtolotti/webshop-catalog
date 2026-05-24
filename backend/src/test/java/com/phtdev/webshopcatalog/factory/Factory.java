package com.phtdev.webshopcatalog.factory;

import com.phtdev.webshopcatalog.dto.ProductDTO;
import com.phtdev.webshopcatalog.entities.Category;
import com.phtdev.webshopcatalog.entities.Product;

import java.math.BigDecimal;

public class Factory {


    public static Product createProduct() {
       Product product = new Product(20L, "Swamp thing",
               "Allan's Moore revamp", new BigDecimal("25.00"), null);

        product.addCategory(new Category(1L, "books"));
        product.addCategory(new Category(2L, "comics"));
       return product;
    }

    public static ProductDTO createProductDto() {
        Product product = createProduct();
        return ProductDTO.create(product);
    }

    public static Category createCategory() {
        return new Category(1L, "Cellphones");
    }
}
