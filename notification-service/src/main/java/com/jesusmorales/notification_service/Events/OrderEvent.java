package com.jesusmorales.notification_service.Events;


import com.jesusmorales.notification_service.Model.Enum.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
