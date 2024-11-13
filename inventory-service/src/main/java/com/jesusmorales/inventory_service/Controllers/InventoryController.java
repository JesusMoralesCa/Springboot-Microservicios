package com.jesusmorales.inventory_service.Controllers;

import com.jesusmorales.inventory_service.Model.Dtos.OrderItemRequest;
import com.jesusmorales.inventory_service.Services.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final IInventoryService inventoryService;

    @GetMapping("/{sku}")
    public ResponseEntity<?> isInStock(@PathVariable("sku") String sku) {
        return ResponseEntity.ok(inventoryService.isInStock(sku));
    }

    @PostMapping("/in-stock")
    public ResponseEntity<?> areInStock(@RequestBody List<OrderItemRequest> orderItems) {
        return ResponseEntity.ok(inventoryService.areInStock(orderItems));
    }
}
