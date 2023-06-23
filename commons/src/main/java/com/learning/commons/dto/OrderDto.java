package com.learning.commons.dto;

import com.learning.commons.enums.OrderStatus;
import com.learning.commons.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Integer userId;
    private Integer productId;
    private Double amount;
    private Integer orderId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;

}
