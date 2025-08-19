package org.example.notificationservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.exception.ConsumeException;
import org.example.notificationservice.model.OrderCreatedEvent;
import org.example.notificationservice.service.MailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final MailService mailService;
    private final ObjectMapper objectMapper;
    @KafkaListener(topics = "order-created", groupId = "order-created-consumer")
    public void consumeOrderCreated(String message) {
        log.info("ActionLog.OrderCreatedConsumer.consume.start - message: {}", message);
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();

        try {
            orderCreatedEvent = objectMapper.readValue(message, OrderCreatedEvent.class);
            mailService.sendMail("Order" + orderCreatedEvent.getOrderId() + "has been created");
        }catch (JsonProcessingException exception){
            log.error("ActionLog.OrderCreatedConsumer.consume.error" , exception);
            throw new ConsumeException(exception.getMessage());
        }

        log.info("ActionLog.OrderCreatedConsumer.consume.end - message: {}", message);
    }
}
