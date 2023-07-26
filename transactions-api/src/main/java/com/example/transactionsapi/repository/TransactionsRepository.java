package com.example.transactionsapi.repository;

import com.example.transactionsapi.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionEntity, Long> {
}
