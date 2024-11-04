package com.jesusmorales.products_service.Repositories;

import com.jesusmorales.products_service.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
