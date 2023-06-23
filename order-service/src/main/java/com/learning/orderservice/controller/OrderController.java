package com.learning.orderservice.controller;

import com.learning.commons.dto.OrderDto;
import com.learning.orderservice.model.Orders;
import com.learning.orderservice.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public OrderController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @PostMapping("/create")
    public Orders createOrder(@RequestBody OrderDto orderDto) {
        return orderServiceImpl.createOrder(orderDto);
    }
}
