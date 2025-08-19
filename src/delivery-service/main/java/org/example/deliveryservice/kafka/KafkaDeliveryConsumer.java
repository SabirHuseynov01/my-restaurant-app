package org.example.deliveryservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliveryservice.dto.OrderConfirmedEvent;
import org.example.deliveryservice.service.DeliveryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaDeliveryConsumer {
    private final DeliveryService deliveryService;



    @KafkaListener(topics = "order-confirmed", groupId = "delivery-service")
    public void consumeOrderConfirmation(OrderConfirmedEvent event) {
        log.info("ActionLog.consumeOrderConfirmed.start - event: {}", event);
        deliveryService.processOrderConfirmation(event);
        log.info("ActionLog.consumeOrderConfirmed.end - event: {}", event);
    }
}
