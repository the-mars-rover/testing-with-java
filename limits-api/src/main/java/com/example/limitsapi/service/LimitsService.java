package com.example.limitsapi.service;

import com.example.limitsapi.repository.AccountLimitsRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.AccountLimitsResponse;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LimitsService {
    private final AccountLimitsRepository accountLimitsRepository;

    public AccountLimitsResponse getAccountLimits(String accountNumber) {
        var optionalAccountLimitsEntity = accountLimitsRepository.findByAccountNumber(accountNumber);

        if (optionalAccountLimitsEntity.isEmpty()) {
            throw new NoSuchElementException();
        }

        var accountLimitsEntity = optionalAccountLimitsEntity.get();
        var accountLimitsResponse = new AccountLimitsResponse();
        accountLimitsResponse.setMin(accountLimitsEntity.getMin());
        accountLimitsResponse.setMax(accountLimitsEntity.getMax());

        return accountLimitsResponse;
    }
}
