package com.learning.orderservice.controller;

import com.learning.commons.dto.OrderDto;
import com.learning.orderservice.model.OrderEntity;
import com.learning.orderservice.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderDto orderDto) {
        OrderEntity order = orderServiceImpl.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
