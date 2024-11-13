package com.jesusmorales.orders_service.Events;

import com.jesusmorales.orders_service.Model.Enum.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
