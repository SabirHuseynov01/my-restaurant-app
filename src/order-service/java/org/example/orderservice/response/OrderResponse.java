package org.example.orderservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.orderservice.request.OrderItemRequest;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long restaurantId;
    private List<OrderItemRequest> items;
    private BigDecimal totalAmount;
    private String status;

}
