package com.example.transactionsapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class TransactionEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String fromAccountNumber;

    @NotNull
    private String toAccountNumber;

    @NotNull
    private Double amount;
}
