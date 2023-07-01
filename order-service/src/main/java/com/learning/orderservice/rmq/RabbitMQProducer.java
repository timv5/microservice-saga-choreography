package com.learning.orderservice.rmq;

import com.learning.commons.dto.Message;
import com.learning.commons.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class RabbitMQProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.start.key}")
    private String routingStartKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceMessage(OrderDto order) {
        Message<OrderDto> message = new Message<>(order, new Date(), UUID.randomUUID());
        LOGGER.info("Publishing event: {}", message);
        this.rabbitTemplate.convertAndSend(exchange, routingStartKey, message);
    }

}
