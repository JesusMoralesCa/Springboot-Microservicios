package com.jesusmorales.inventory_service.Services;

import com.jesusmorales.inventory_service.Model.Dtos.BaseResponse;
import com.jesusmorales.inventory_service.Model.Dtos.OrderItemRequest;
import com.jesusmorales.inventory_service.Model.Entities.Inventory;
import com.jesusmorales.inventory_service.Repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService implements IInventoryService{

    private final InventoryRepository inventoryRepository;

    @Override
    public boolean isInStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);

        return inventory.filter(value -> value.getQuantity() > 0).isPresent();
    }

    @Override
    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {
        // Extraemos los SKUs de los productos solicitados
        List<String> skus = orderItems.stream().map(OrderItemRequest::getSku).toList();

        // Obtenemos los inventarios de los SKUs solicitados
        List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skus);

        // Creamos un mapa para acceder a los inventarios por SKU
        var inventoryMap = inventoryList.stream()
                .collect(Collectors.toMap(Inventory::getSku, inventory -> inventory));

        // Recorremos los items de la orden para verificar existencia y cantidad
        List<String> errors = orderItems.stream()
                .filter(orderItem -> {
                    Inventory inventory = inventoryMap.get(orderItem.getSku());
                    return inventory == null || inventory.getQuantity() < orderItem.getQuantity();
                })
                .map(orderItem -> {
                    Inventory inventory = inventoryMap.get(orderItem.getSku());
                    if (inventory == null) {
                        return "Product with sku " + orderItem.getSku() + " does not exist";
                    } else {
                        return "Product with sku " + orderItem.getSku() + " has insufficient quantity";
                    }
                })
                .toList();

        // Retornamos la respuesta con errores si los hay
        return errors.isEmpty() ? new BaseResponse(null) : new BaseResponse(errors.toArray(new String[0]));
    }

}
