package org.example.deliveryservice.service;

import org.example.deliveryservice.dto.OrderConfirmedEvent;

public interface DeliveryService {
    void processOrderConfirmation(OrderConfirmedEvent event);
}
