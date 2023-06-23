package com.learning.paymentservice.repository;

import com.learning.paymentservice.model.UserBalances;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalancesRepository extends JpaRepository<UserBalances, Integer> {
}
