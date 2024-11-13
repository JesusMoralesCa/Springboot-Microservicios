package com.jesusmorales.orders_service.Services;

import com.jesusmorales.orders_service.Model.Dtos.OrderRequest;
import com.jesusmorales.orders_service.Model.Dtos.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);

    List<OrderResponse> getAllOrders();
}
