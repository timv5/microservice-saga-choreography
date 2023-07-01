package com.learning.orderservice.service;

import com.learning.commons.dto.Message;
import com.learning.commons.dto.OrderDto;
import com.learning.commons.dto.PaymentDto;
import com.learning.orderservice.model.OrderEntity;

public interface OrderService {

    OrderEntity createOrder(OrderDto orderDto);

    void completeOrder(Message<PaymentDto> paymentDto);

}
