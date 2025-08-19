package org.example.orderservice.service;

import org.example.orderservice.request.CreateOrderRequest;

public interface OrderService {

    void createOrder(CreateOrderRequest request);

    void confirmOrder(Long orderId);
}
