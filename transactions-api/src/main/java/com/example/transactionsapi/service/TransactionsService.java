package com.example.transactionsapi.service;

import com.example.transactionsapi.entity.TransactionEntity;
import com.example.transactionsapi.repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.LimitsApiClient;
import org.openapitools.model.TransactionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionsService {
    private final TransactionsRepository transactionsRepository;

    private final LimitsApiClient limitsApiClient;

    public void processTransaction(TransactionRequest transactionRequest) {
        var accountLimitsResponse = Objects.requireNonNull(
                limitsApiClient.getAccountLimits(transactionRequest.getFrom()).getBody());

        if (accountLimitsResponse.getMax() <= transactionRequest.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction amount exceeds account limit");
        }

        if (accountLimitsResponse.getMin() > transactionRequest.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction amount exceeds account limit");
        }

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setFromAccountNumber(transactionRequest.getFrom());
        transactionEntity.setToAccountNumber(transactionRequest.getTo());
        transactionEntity.setAmount(transactionRequest.getAmount());
        transactionsRepository.save(transactionEntity);
    }
}
