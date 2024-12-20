package com.jesusmorales.products_service.Controllers;

import com.jesusmorales.products_service.Services.IProductService;
import com.jesusmorales.products_service.model.dtos.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest) {
        productService.addProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado");
    }


    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
