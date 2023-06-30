package com.learning.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.commons.dto.OrderDto;
import com.learning.commons.enums.OrderStatus;
import com.learning.orderservice.model.Orders;
import com.learning.orderservice.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderServiceImpl orderService;

    @Test
    public void createOrder_success() throws Exception {
        OrderDto orderDto = OrderDto.builder()
                .userId(1)
                .productId(1)
                .amount(2000.0)
                .build();

        Orders expected = Orders.builder()
                .userId(orderDto.getUserId())
                .productId(orderDto.getProductId())
                .price(orderDto.getAmount())
                .orderStatus(OrderStatus.ORDER_CREATE)
                .build();

        when(orderService.createOrder(orderDto)).thenReturn(expected);

        MvcResult result = mockMvc.perform(post("/api/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto))
        ).andExpect(status().isCreated()).andReturn();

        Orders actual = objectMapper.readValue(result.getResponse().getContentAsString(), Orders.class);
        assertEquals(expected, actual);
    }

}