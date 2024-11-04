package com.jesusmorales.orders_service.Model.Dtos;

public record OrderItemsResponse(Long id, String sku, Double price, Long quantity) {
}
