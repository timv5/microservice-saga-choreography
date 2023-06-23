package com.learning.paymentservice.rmq;

import com.learning.commons.dto.Message;
import com.learning.commons.dto.PaymentDto;
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

    @Value("${rabbitmq.routing.complete.key}")
    private String routingCompleteKey;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceMessage(PaymentDto paymentDto) {
        Message<PaymentDto> message = new Message<>(paymentDto, new Date(), UUID.randomUUID());
        LOGGER.info("Publishing event: {}", message);
        rabbitTemplate.convertAndSend(exchange, routingCompleteKey, message);
    }
}
