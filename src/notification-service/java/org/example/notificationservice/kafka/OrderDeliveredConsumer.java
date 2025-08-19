package org.example.notificationservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.exception.ConsumeException;
import org.example.notificationservice.model.OrderDeliveredEvent;
import org.example.notificationservice.service.MailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderDeliveredConsumer {
    private final MailService mailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-delivered", groupId = "order-delivery-group")
    public void consume(String message) {
        log.info("ActionLog.OrderDeliveredConsumer.start - message: {}", message);
        OrderDeliveredEvent orderDeliveredEvent = new OrderDeliveredEvent();
        try {
            orderDeliveredEvent = objectMapper.readValue(message, OrderDeliveredEvent.class);

        }catch (JsonProcessingException exception){
            log.error("ActionLog.OrderDeliveredConsumer.consume.error",exception );
            throw new ConsumeException(exception.getMessage());
        }


        mailService.sendMail("Order " + orderDeliveredEvent.getOrderId() + "Your order has been delivered");

        log.info("ActionLog.OrderDeliveredConsumer.end - message: {}", message);

    }
}
