package com.example.limitsapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class AccountLimitsEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String accountNumber;

    @NotNull
    private Double min;

    @NotNull
    private Double max;
}
