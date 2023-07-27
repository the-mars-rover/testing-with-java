package com.example.limitsapi.testing;

import com.example.limitsapi.entity.AccountLimitsEntity;
import org.openapitools.model.AccountLimitsResponse;

public class MockTestData {

    // Do re-use mock data in multiple unit tests (can be loaded as resources too)
    public static AccountLimitsEntity getAccountLimitsEntity() {
        AccountLimitsEntity mockAccountLimitsEntity = new AccountLimitsEntity();
        mockAccountLimitsEntity.setId(1L);
        mockAccountLimitsEntity.setAccountNumber("000000001");
        mockAccountLimitsEntity.setMin(10.0);
        mockAccountLimitsEntity.setMax(10000.0);
        return mockAccountLimitsEntity;
    }

    public static AccountLimitsResponse getAccountLimitsResponse() {
        AccountLimitsResponse mockAccountLimitsResponse = new AccountLimitsResponse();
        mockAccountLimitsResponse.setMin(10.0);
        mockAccountLimitsResponse.setMax(10000.0);
        return mockAccountLimitsResponse;
    }
}
