package com.example.transactionsapi.badtests;

import com.example.transactionsapi.service.TransactionsService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

import java.io.InputStream;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://prod:5432/transactionsdb",
        "spring.datasource.username=prod_db_username",
        "spring.datasource.password=prod_db_password",
        "limits.url=https://prod.limitsapi.com"}) // Do not call live services
@Disabled
class BadTransactionsApiApplicationTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean // Do not mock the unit you are testing
    TransactionsService transactionsService;

    @Test
    void givenValidTransactionAmount_whenPostTransaction_thenOk() throws Exception {
        // Given
        InputStream inputStream = BadTransactionsApiApplicationTest.class
                .getResourceAsStream("/json/transaction-request-valid-amount.json");
        String json = new String(Objects.requireNonNull(inputStream).readAllBytes());

        // When
        ResultActions resultActions = mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));

        // Then
        resultActions.andExpect(status().isOk());
    }
}