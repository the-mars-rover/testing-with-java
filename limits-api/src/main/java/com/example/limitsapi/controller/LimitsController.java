package com.example.limitsapi.controller;

import com.example.limitsapi.service.LimitsService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.LimitsApi;
import org.openapitools.model.AccountLimitsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class LimitsController implements LimitsApi {

    private final LimitsService limitsService;

    @Override
    public ResponseEntity<AccountLimitsResponse> getAccountLimits(String accountNumber) {
        var accountLimitsResponse = limitsService.getAccountLimits(accountNumber);

        return ResponseEntity.ok(accountLimitsResponse);
    }
}
