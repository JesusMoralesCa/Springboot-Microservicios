package com.jesusmorales.orders_service.Repositories;

import com.jesusmorales.orders_service.Model.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
