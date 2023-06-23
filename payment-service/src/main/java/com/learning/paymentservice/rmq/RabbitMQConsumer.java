package com.learning.paymentservice.rmq;

import com.learning.commons.dto.Message;
import com.learning.commons.dto.OrderDto;
import com.learning.paymentservice.service.PaymentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    private final PaymentServiceImpl paymentService;

    @Autowired
    public RabbitMQConsumer(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.start.name}"})
    public void consumeMessage(Message<OrderDto> message) {
        LOGGER.info("Message received: {} with global id {}", message, message.getIdentifier());
        paymentService.completePayment(message);
    }

}
