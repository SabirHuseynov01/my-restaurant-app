package org.example.deliveryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliveryservice.dto.OrderConfirmedEvent;
import org.example.deliveryservice.entity.DeliveryEntity;
import org.example.deliveryservice.enums.DeliveryStatus;
import org.example.deliveryservice.kafka.KafkaDeliveryProducer;
import org.example.deliveryservice.repository.DeliveryRepository;
import org.example.deliveryservice.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final KafkaDeliveryProducer kafkaDeliveryProducer;

    @Override
    public void processOrderConfirmation(OrderConfirmedEvent event) {
        log.info("ActionLog.processOrderConfirmation.start - event: {}", event);
        var deliveryEntity = new DeliveryEntity();
        deliveryEntity.setOrderId(event.getOrderId());
        deliveryEntity.setRestaurantId(event.getRestaurantId());
        deliveryEntity.setStatus(DeliveryStatus.IN_PROGRESS);
        deliveryRepository.save(deliveryEntity);

        new Thread(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(20).toMillis());
                deliveryEntity.setStatus(DeliveryStatus.DELIVERED);
                deliveryEntity.setDeliveredAt(LocalDateTime.now());
                deliveryRepository.save(deliveryEntity);

                var orderDeliveredEvent = new OrderConfirmedEvent();
                orderDeliveredEvent.setOrderId(event.getOrderId());
                orderDeliveredEvent.setRestaurantId(event.getRestaurantId());

                kafkaDeliveryProducer.publishOrderDelivered(orderDeliveredEvent);
            } catch (InterruptedException exception){
                log.error("ActionLog.processOrderConfirmation.error - Interrupted thread", exception);
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
