package org.example.orderservice.mapper;

import lombok.experimental.UtilityClass;
import org.example.orderservice.entity.OrderEntity;
import org.example.orderservice.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.Map;

@UtilityClass
public class OrderMapper {
    public static OrderEntity toEntity(Long restaurantId, String itemsJson, BigDecimal totalPrice) {
        return OrderEntity.builder()
                .restaurantId(restaurantId)
                .itemsJson(itemsJson)
                .totalPrice(totalPrice)
                .status(OrderStatus.PENDING)
                .build();
    }

    public static Map<String,Object> buildItemDetails(Long itemId, String name,
                                                      BigDecimal price,Integer quantity) {
        return Map.of(
                "itemId", itemId,
                "name", name,
                "price", price,
                "quantity", quantity);
    }
}
