package com.learning.commons.dto;

import com.learning.commons.enums.OrderStatus;
import com.learning.commons.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {

    private Integer orderId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;

}
