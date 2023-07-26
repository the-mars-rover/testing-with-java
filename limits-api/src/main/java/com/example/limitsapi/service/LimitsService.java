package com.example.limitsapi.service;

import com.example.limitsapi.repository.AccountLimitsRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.AccountLimitsResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LimitsService {
    private final AccountLimitsRepository accountLimitsRepository;

    public AccountLimitsResponse getAccountLimits(String accountNumber) {
        var accountLimitsEntity = accountLimitsRepository.findByAccountNumber(accountNumber).orElseThrow();

        var accountLimitsResponse = new AccountLimitsResponse();
        accountLimitsResponse.setMin(accountLimitsEntity.getMin());
        accountLimitsResponse.setMax(accountLimitsEntity.getMax());

        return accountLimitsResponse;
    }
}
