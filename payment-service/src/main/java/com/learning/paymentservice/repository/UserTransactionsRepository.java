package com.learning.paymentservice.repository;

import com.learning.paymentservice.model.UserTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionsRepository extends JpaRepository<UserTransactions, Integer> {
}
