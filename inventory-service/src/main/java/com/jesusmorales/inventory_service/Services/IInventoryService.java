package com.jesusmorales.inventory_service.Services;

import com.jesusmorales.inventory_service.Model.Dtos.BaseResponse;
import com.jesusmorales.inventory_service.Model.Dtos.OrderItemRequest;

import java.util.List;

public interface IInventoryService {
    boolean isInStock(String sku);

    BaseResponse areInStock(List<OrderItemRequest> orderItems);
}
