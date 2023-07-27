package com.example.limitsapi.repository;

import com.example.limitsapi.entity.AccountLimitsEntity;
import com.example.limitsapi.testing.MockTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountLimitsRepositoryTest {

    @Autowired
    AccountLimitsRepository accountLimitsRepository;

    @Test
    void givenLimitsForAccountInDatabase_whenFindByAccountNumber_theReturnOptionalOfLimits() {
        // Given
        AccountLimitsEntity accountLimitsEntity = MockTestData.getAccountLimitsEntity();
        accountLimitsRepository.save(accountLimitsEntity);

        // When
        var accountLimits = accountLimitsRepository.findByAccountNumber(accountLimitsEntity.getAccountNumber());

        // Then
        assertTrue(accountLimits.isPresent());
        assertEquals(accountLimitsEntity.getAccountNumber(), accountLimits.get().getAccountNumber());
        assertEquals(accountLimitsEntity.getMax(), accountLimits.get().getMax());
        assertEquals(accountLimitsEntity.getMin(), accountLimits.get().getMin());
    }

    @Test
    void givenNoLimitsForAccountInDatabase_whenFindByAccountNumber_theReturnOptionalOfEmpty() {
        // Given  When
        var accountLimits = accountLimitsRepository.findByAccountNumber("000000002");

        // Then
        assertTrue(accountLimits.isEmpty());
    }
}