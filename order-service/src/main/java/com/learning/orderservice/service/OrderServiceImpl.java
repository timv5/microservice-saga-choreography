package com.learning.orderservice.service;

import com.learning.commons.dto.Message;
import com.learning.commons.dto.OrderDto;
import com.learning.commons.dto.PaymentDto;
import com.learning.commons.enums.OrderStatus;
import com.learning.commons.enums.PaymentStatus;
import com.learning.orderservice.model.Orders;
import com.learning.orderservice.repository.OrderRepository;
import com.learning.orderservice.rmq.RabbitMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, RabbitMQProducer rabbitMQProducer) {
        this.orderRepository = orderRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Transactional
    public Orders createOrder(OrderDto orderDto) {
        Orders order = orderRepository.save(createOrderEntity(orderDto));
        orderDto.setOrderId(order.getId());
        orderDto.setOrderStatus(OrderStatus.ORDER_CREATE);
        orderDto.setPaymentStatus(PaymentStatus.PAYMENT_STARTED);
        LOGGER.info("Order saved: {}", order);

        rabbitMQProducer.produceMessage(orderDto);

        return order;
    }

    @Transactional
    @Override
    public void completeOrder(Message<PaymentDto> message) {
        PaymentDto paymentDto = message.getData();
        OrderStatus orderStatus = orderRepository.findById(paymentDto.getOrderId())
                .map(order -> {
                    OrderStatus newStatus;
                    if (PaymentStatus.PAYMENT_COMPLETED.equals(paymentDto.getPaymentStatus())) {
                        newStatus = OrderStatus.ORDER_COMPLETED;
                    } else {
                        newStatus = OrderStatus.ORDER_CANCELLED;
                    }

                    order.setOrderStatus(newStatus);
                    order.setPaymentStatus(paymentDto.getPaymentStatus());
                    return paymentDto.getOrderStatus();
                }).orElseThrow();
        LOGGER.info("Order {} completed with a status: {}", paymentDto.getOrderId(), orderStatus);
    }

    protected Orders createOrderEntity(OrderDto orderDto) {
        return Orders.builder()
                .userId(orderDto.getUserId())
                .productId(orderDto.getProductId())
                .price(orderDto.getAmount())
                .orderStatus(OrderStatus.ORDER_CREATE)
                .build();
    }

}
