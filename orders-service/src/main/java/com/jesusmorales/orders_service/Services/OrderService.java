package com.jesusmorales.orders_service.Services;

import com.jesusmorales.orders_service.Events.OrderEvent;
import com.jesusmorales.orders_service.Model.Dtos.BaseResponse;
import com.jesusmorales.orders_service.Model.Dtos.OrderItemsResponse;
import com.jesusmorales.orders_service.Model.Dtos.OrderRequest;
import com.jesusmorales.orders_service.Model.Dtos.OrderResponse;
import com.jesusmorales.orders_service.Model.Entities.Order;
import com.jesusmorales.orders_service.Model.Entities.OrderItems;
import com.jesusmorales.orders_service.Model.Enum.OrderStatus;
import com.jesusmorales.orders_service.Repositories.OrderRepository;
import com.jesusmorales.orders_service.Utils.JsonUtils;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObservationRegistry observationRegistry;

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        Observation inventoryObservation = Observation.createNotStarted("inventory-service", observationRegistry);

        return inventoryObservation.observe(() -> {

            // Verifica el inventario
            BaseResponse result = this.webClientBuilder.build()
                    .post()
                    .uri("lb://inventory-service/api/inventory/in-stock")
                    .bodyValue(orderRequest.getOrderItems())
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();

            if (result != null && !result.hasErrors()) {
                Order order = new Order();
                order.setOrderNumber(UUID.randomUUID().toString());

                // Crear directamente los OrderItems
                List<OrderItems> orderItems = orderRequest.getOrderItems().stream()
                        .map(orderItemRequest -> OrderItems.builder()
                                .id(orderItemRequest.getId())
                                .sku(orderItemRequest.getSku())
                                .price(orderItemRequest.getPrice())
                                .quantity(orderItemRequest.getQuantity())
                                .order(order)
                                .build())
                        .collect(Collectors.toList());

                order.setOrderItems(orderItems);
                Order savedOrder = this.orderRepository.save(order);

                //TODO: Send message to order topic
                kafkaTemplate.send("orders-topic",JsonUtils.toJson(
                        new OrderEvent(
                                savedOrder.getOrderNumber(),
                                savedOrder.getOrderItems().size(),
                                OrderStatus.PLACED
                        )
                ));



                // Convertir el Order guardado a OrderResponse
                return new OrderResponse(
                        savedOrder.getId(),
                        savedOrder.getOrderNumber(),
                        savedOrder.getOrderItems().stream()
                                .map(orderItem -> new OrderItemsResponse(
                                        orderItem.getId(),
                                        orderItem.getSku(),
                                        orderItem.getPrice(),
                                        orderItem.getQuantity()))
                                .collect(Collectors.toList())
                );
            } else {
                throw new IllegalArgumentException("Some of the products are not in stock");
            }
        });
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        // Obtener todas las órdenes y convertirlas directamente
        return this.orderRepository.findAll().stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getOrderNumber(),
                        order.getOrderItems().stream()
                                .map(orderItem -> new OrderItemsResponse(
                                        orderItem.getId(),
                                        orderItem.getSku(),
                                        orderItem.getPrice(),
                                        orderItem.getQuantity()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

}
