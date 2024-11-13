package com.jesusmorales.orders_service.Controllers;

import com.jesusmorales.orders_service.Model.Dtos.OrderRequest;
import com.jesusmorales.orders_service.Services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/addOrder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Orden creada");
    }


    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

}
