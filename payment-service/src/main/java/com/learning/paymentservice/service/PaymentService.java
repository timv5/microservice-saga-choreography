package com.learning.paymentservice.service;

import com.learning.commons.dto.Message;
import com.learning.commons.dto.OrderDto;

public interface PaymentService {

    void completePayment(Message<OrderDto> orderDto);

}
