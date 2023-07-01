package com.learning.orderservice.repository;

import com.learning.orderservice.AbstractDatabase;
import com.learning.orderservice.DbIntegrationTest;
import com.learning.orderservice.model.OrderEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DbIntegrationTest
public class OrderRepositoryTest extends AbstractDatabase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void save_success() {
        OrderEntity orderEntity = OrderEntity.builder()
                .userId(1)
                .productId(1)
                .price(2000.0)
                .build();

        // save entity
        orderEntity = orderRepository.save(orderEntity);
        Optional<OrderEntity> savedEntityOptional = orderRepository.findById(orderEntity.getId());
        if (savedEntityOptional.isEmpty()) {
            fail();
        }
        OrderEntity savedEntity = savedEntityOptional.get();

        // so that we can get DB generated fields
        entityManager.flush();

        // assert that saved and retrieved columns are the same
        assertEquals(orderEntity.getUserId(), savedEntity.getUserId());
        assertEquals(orderEntity.getProductId(), savedEntity.getProductId());
        assertEquals(orderEntity.getPrice(), savedEntity.getPrice());

        // assert that new saved fields are not null
        assertNotNull(savedEntity.getId());
    }

}