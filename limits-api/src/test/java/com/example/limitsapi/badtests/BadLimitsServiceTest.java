package com.example.limitsapi.badtests; // Don't deviate from the package structure of the class you are testing

import com.example.limitsapi.entity.AccountLimitsEntity;
import com.example.limitsapi.repository.AccountLimitsRepository;
import com.example.limitsapi.service.LimitsService;
import com.example.limitsapi.testing.MockTestData;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openapitools.model.AccountLimitsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LimitsService.class)
@Disabled
class BadLimitsServiceTest {

    @Autowired // Do not inject dependencies other than the unit you are testing
    AccountLimitsRepository accountLimitsRepository;

    @Autowired
    LimitsService limitsService;

    @Test
    void testAccountLimits() {
        // Do not use unclear test names

        AccountLimitsEntity mockAccountLimitsEntity = MockTestData.getAccountLimitsEntity();
        when(accountLimitsRepository.findByAccountNumber(mockAccountLimitsEntity.getAccountNumber()))
                .thenReturn(Optional.of(mockAccountLimitsEntity));
        AccountLimitsResponse accountLimitsResponse =
                limitsService.getAccountLimits(mockAccountLimitsEntity.getAccountNumber());
        assertNotNull(accountLimitsResponse);
        assertEquals(mockAccountLimitsEntity.getMin(), accountLimitsResponse.getMin());
        assertEquals(mockAccountLimitsEntity.getMax(), accountLimitsResponse.getMax());

        // Do not use one unit test to test multiple scenarios
        when(accountLimitsRepository.findByAccountNumber("000000002"))
                .thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> limitsService.getAccountLimits("000000002"));
    }
}