package com.learning.paymentservice.service;

import com.learning.commons.dto.Message;
import com.learning.commons.dto.OrderDto;
import com.learning.commons.dto.PaymentDto;
import com.learning.commons.enums.OrderStatus;
import com.learning.commons.enums.PaymentStatus;
import com.learning.paymentservice.model.UserTransactions;
import com.learning.paymentservice.repository.UserBalancesRepository;
import com.learning.paymentservice.repository.UserTransactionsRepository;
import com.learning.paymentservice.rmq.RabbitMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final UserBalancesRepository userBalancesRepository;
    private final UserTransactionsRepository userTransactionsRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public PaymentServiceImpl(UserBalancesRepository userBalancesRepository,
                              UserTransactionsRepository userTransactionsRepository,
                              RabbitMQProducer rabbitMQProducer) {
        this.userBalancesRepository = userBalancesRepository;
        this.userTransactionsRepository = userTransactionsRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Transactional
    @Override
    public void completePayment(Message<OrderDto> message) {
        OrderDto order = message.getData();

        LOGGER.info("Processing payment for order: {}", order);
        PaymentDto paymentDto = userBalancesRepository.findById(order.getUserId())
                .filter(balance -> balance.getBalance() > order.getAmount())
                .map(balance -> {
                    balance.setBalance(balance.getBalance() - order.getAmount());
                    saveTransaction(order.getOrderId(), order.getAmount(), order.getUserId());
                    return new PaymentDto(order.getOrderId(), OrderStatus.ORDER_CREATE, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentDto(order.getOrderId(), OrderStatus.ORDER_CANCELLED, PaymentStatus.PAYMENT_FAILED));
        rabbitMQProducer.produceMessage(paymentDto);
    }

    private void saveTransaction(Integer orderId, Double amount, Integer userId) {
        userTransactionsRepository.save(new UserTransactions(orderId, amount, -1, userId));
    }
}
