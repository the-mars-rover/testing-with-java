package com.example.limitsapi.service; // Do use the same package structure as the code

import com.example.limitsapi.entity.AccountLimitsEntity;
import com.example.limitsapi.repository.AccountLimitsRepository;
import com.example.limitsapi.testing.MockTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openapitools.model.AccountLimitsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

// Use Mockito as well
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LimitsService.class)
class LimitsServiceTest {

    @MockBean // Do mock out dependencies
    AccountLimitsRepository accountLimitsRepository;

    @Autowired
    LimitsService limitsService;

    @Test
    void givenAccountLimitInDatabase_whenGetAccountLimits_thenReturnAccountLimitsResponse() {
        // Do use given, when, then (or input, action, output) structure

        // Given
        AccountLimitsEntity mockAccountLimitsEntity = MockTestData.getAccountLimitsEntity();
        when(accountLimitsRepository.findByAccountNumber(mockAccountLimitsEntity.getAccountNumber()))
                .thenReturn(Optional.of(mockAccountLimitsEntity));

        // When
        AccountLimitsResponse accountLimitsResponse =
                limitsService.getAccountLimits(mockAccountLimitsEntity.getAccountNumber());

        // Then
        assertNotNull(accountLimitsResponse);
        assertEquals(mockAccountLimitsEntity.getMin(), accountLimitsResponse.getMin());
        assertEquals(mockAccountLimitsEntity.getMax(), accountLimitsResponse.getMax());
    }

    @Test
    void givenNoAccountLimitInDatabase_whenGetAccountLimits_thenThrowNoValueException() {
        // Do use seperate unit tests for each scenario

        // Given
        when(accountLimitsRepository.findByAccountNumber(anyString()))
                .thenReturn(Optional.empty());

        // When Then
        assertThrows(NoSuchElementException.class,
                () -> limitsService.getAccountLimits("000000001"));
    }
}