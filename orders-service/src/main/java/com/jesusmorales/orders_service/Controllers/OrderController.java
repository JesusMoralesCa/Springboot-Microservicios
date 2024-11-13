package com.jesusmorales.orders_service.Controllers;

import com.jesusmorales.orders_service.Model.Dtos.OrderRequest;
import com.jesusmorales.orders_service.Model.Dtos.OrderResponse;
import com.jesusmorales.orders_service.Services.IOrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "orders-service", fallbackMethod = "placeOrderFallback")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.placeOrder(orderRequest));
    }


    @GetMapping("/getAllOrders")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    private ResponseEntity<OrderResponse> placeOrderFallback(OrderRequest orderRequest, Throwable throwable) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

}
