package com.jesusmorales.products_service.Services;

import com.jesusmorales.products_service.Repositories.ProductRepository;
import com.jesusmorales.products_service.model.dtos.ProductRequest;
import com.jesusmorales.products_service.model.entities.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService{

    private final ProductRepository productRepository;

    @Override
    public void addProduct(ProductRequest productRequest) {
        var product = Product.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .status(productRequest.getStatus())
                .build();

        productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> listProducts = productRepository.findAll();
        return listProducts;
    }
}




