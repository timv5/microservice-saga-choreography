package com.learning.paymentservice.repository;

import com.learning.paymentservice.model.UserTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionsRepository extends JpaRepository<UserTransactionEntity, Integer> {
}
