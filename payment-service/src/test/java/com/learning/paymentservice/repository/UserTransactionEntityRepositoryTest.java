package com.learning.paymentservice.repository;

import com.learning.paymentservice.model.UserTransactionEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DbIntegrationTest
public class UserTransactionEntityRepositoryTest extends AbstractDatabase {

    @Autowired
    private UserTransactionsRepository userTransactionsRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void save_success() {
        UserTransactionEntity userTransactionEntity = UserTransactionEntity.builder()
                .orderId(1)
                .amount(2000.0)
                .factor(-1)
                .userId(1)
                .build();

        userTransactionEntity = userTransactionsRepository.save(userTransactionEntity);
        Optional<UserTransactionEntity> savedTransactionEntityOptional = userTransactionsRepository.findById(userTransactionEntity.getOrderId());
        if (savedTransactionEntityOptional.isEmpty()) {
            fail();
        }
        UserTransactionEntity storedEntity = savedTransactionEntityOptional.get();

        entityManager.flush();

        assertEquals(userTransactionEntity.getOrderId(), storedEntity.getOrderId());
        assertEquals(userTransactionEntity.getUserId(), storedEntity.getUserId());
        assertEquals(userTransactionEntity.getFactor(), storedEntity.getFactor());
        assertEquals(userTransactionEntity.getAmount(), storedEntity.getAmount());
    }

}