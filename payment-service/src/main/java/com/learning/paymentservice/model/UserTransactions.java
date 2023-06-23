package com.learning.paymentservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "USER_TRANSACTIONS")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTransactions {

    @Id
    @GeneratedValue
    private Integer orderId;
    private Double amount;
    private Integer factor;
    private Integer userId;

}
