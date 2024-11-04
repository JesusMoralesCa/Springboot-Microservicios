package com.jesusmorales.products_service.Services;

import com.jesusmorales.products_service.model.dtos.ProductRequest;
import com.jesusmorales.products_service.model.entities.Product;

import java.util.List;

public interface IProductService {
    void addProduct(ProductRequest productRequest);

    List<Product> getAllProducts();
}
