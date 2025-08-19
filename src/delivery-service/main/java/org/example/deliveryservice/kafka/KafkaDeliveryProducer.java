package org.example.deliveryservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deliveryservice.dto.OrderConfirmedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaDeliveryProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void publishOrderDelivered(OrderConfirmedEvent event) {
        log.info("ActionLog.publishOrderDelivered.start - event: {}", event);
        kafkaTemplate.send("order-delivered", event);
        log.info("ActionLog.publishOrderDelivered.end - event: {}", event);

    }

}
