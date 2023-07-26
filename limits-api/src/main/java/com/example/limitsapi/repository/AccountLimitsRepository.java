package com.example.limitsapi.repository;

import com.example.limitsapi.entity.AccountLimitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountLimitsRepository extends JpaRepository<AccountLimitsEntity, Long> {
    Optional<AccountLimitsEntity> findByAccountNumber(String accountNumber);
}
