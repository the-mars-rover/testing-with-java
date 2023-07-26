package com.example.transactionsapi.controller;


import com.example.transactionsapi.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.TransactionsApi;
import org.openapitools.model.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TransactionsController implements TransactionsApi {

    private final TransactionsService transactionsService;

    @Override
    public ResponseEntity<Void> postTransaction(TransactionRequest transactionRequest) {
        transactionsService.processTransaction(transactionRequest);

        return ResponseEntity.ok().build();
    }
}
