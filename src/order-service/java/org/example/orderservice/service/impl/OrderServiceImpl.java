package org.example.orderservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.client.MenuClient;
import org.example.orderservice.dto.OrderConfirmedEvent;
import org.example.orderservice.dto.OrderCreatedEvent;
import org.example.orderservice.entity.OrderEntity;
import org.example.orderservice.enums.OrderStatus;
import org.example.orderservice.exception.NotFoundException;
import org.example.orderservice.kafka.Kafka;
import org.example.orderservice.mapper.OrderMapper;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.request.CreateOrderRequest;
import org.example.orderservice.request.OrderItemRequest;
import org.example.orderservice.service.OrderService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final MenuClient menuClient;
    private final Kafka kafka;

    @Override
    public void createOrder(CreateOrderRequest request) {
        log.info("ActionLog.createOrder.start - request: {}", request);
        var total = BigDecimal.ZERO;
        List<Map<String, Object>> items = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {
            var menuItem = menuClient.getMenuItemById(itemRequest.getMenuItemId());
            var price = menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            total = total.add(price);

            Map<String, Object> entry = OrderMapper.buildItemDetails(
                    itemRequest.getMenuItemId(),
                    menuItem.getName(),
                    menuItem.getPrice(),
                    itemRequest.getQuantity()
            );

            items.add(entry);

        }

        var itemsJson = new Gson().toJson(items);
        var entity = OrderMapper.toEntity(
                request.getRestaurantId(),
                itemsJson,
                total);

        var savedEntity = orderRepository.save(entity);

        var orderCreatedEvent = new OrderCreatedEvent(
                savedEntity.getId(),
                savedEntity.getRestaurantId(),
                savedEntity.getTotalPrice()
        );

        kafka.sendMessage("order-created", orderCreatedEvent);
        log.info("ActionLog.createOrder.end - orderId: {}", savedEntity.getId());
    }

    @Override
    public void confirmOrder(Long orderId) {
        log.info("ActionLog.confirmOrder.start - orderId: {}", orderId);
        orderRepository.findById(orderId)
                .ifPresentOrElse(order -> {
                    order.setStatus(OrderStatus.CONFIRMED);
                    orderRepository.save(order);
                    kafka.send("order-confirmed", new OrderConfirmedEvent(
                            order.getId(),
                            order.getRestaurantId()
                    ));
                    log.info("ActionLog.confirmOrder.end - orderId: {}, status: {}", orderId, order.getStatus());
                }, () -> {
                    log.error("ActionLog.confirmOrder.error - order not found with id: {}", orderId);
                    throw new NotFoundException("Order not found with id: " + orderId);
                });
    }
}
