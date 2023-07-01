package com.learning.paymentservice.repository;

import com.learning.paymentservice.model.UserBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalancesRepository extends JpaRepository<UserBalanceEntity, Integer> {
}
