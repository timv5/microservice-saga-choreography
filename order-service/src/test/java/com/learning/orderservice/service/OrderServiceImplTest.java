package com.learning.orderservice.service;

import com.learning.commons.dto.OrderDto;
import com.learning.orderservice.model.Orders;
import com.learning.orderservice.repository.OrderRepository;
import com.learning.orderservice.rmq.RabbitMQProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_success() {
        OrderDto orderDto = OrderDto.builder()
                .userId(1)
                .productId(1)
                .amount(2000.0)
                .build();
        when(orderRepository.save(any(Orders.class))).thenReturn(orderService.createOrderEntity(orderDto));
        doNothing().when(rabbitMQProducer).produceMessage(orderDto);

        Orders actual = orderService.createOrder(orderDto);
        ArgumentCaptor<Orders> orderEntityCaptor = ArgumentCaptor.forClass(Orders.class);
        verify(orderRepository).save(orderEntityCaptor.capture());

        // correctly saved
        Orders savedOrderEntity = orderEntityCaptor.getValue();
        assertEquals(orderDto.getUserId(), savedOrderEntity.getUserId());
        assertEquals(orderDto.getProductId(), savedOrderEntity.getProductId());
        assertEquals(orderDto.getAmount(), savedOrderEntity.getPrice());

        // correct mapping between entity and dto
        assertEquals(savedOrderEntity.getUserId(), actual.getUserId());
        assertEquals(savedOrderEntity.getProductId(), actual.getProductId());
        assertEquals(savedOrderEntity.getPrice(), actual.getPrice());
    }
}