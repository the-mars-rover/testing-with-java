package com.example.transactionsapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionsApiApplicationTest {
    @Autowired
    MockMvc mockMvc;

    @Container // Do use embedded/containerized dependencies.
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("transactionsdb")
            .withUsername("user")
            .withPassword("password")
            .withCopyFileToContainer(
                    MountableFile.forClasspathResource("init-transactionsdb.sql"),
                    "/docker-entrypoint-initdb.d/init.sql");

    @Container
    static GenericContainer<?> wiremock = new GenericContainer<>("wiremock/wiremock:latest")
            .withExposedPorts(8080)
            .withCommand("-global-response-templating", "-verbose")
            .waitingFor(Wait.forListeningPort())
            .withCopyFileToContainer(
                    MountableFile.forClasspathResource("wiremock"),
                    "/home/wiremock");

    static {
        Startables.deepStart(postgres, wiremock).join();
    }

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("limits.url", () -> wiremock.getHost() + ":" + wiremock.getFirstMappedPort());
    }

    @Test
    void givenValidTransactionAmount_whenPostTransaction_thenOk() throws Exception {
        // Given
        InputStream inputStream = TransactionsApiApplicationTest.class
                .getResourceAsStream("/json/transaction-request-valid-amount.json");
        String json = new String(Objects.requireNonNull(inputStream).readAllBytes());

        // When
        ResultActions resultActions = mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json));

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void givenTransactionAmountTooLarge_whenPostTransaction_thenBadRequest() throws Exception {
        // Given
        InputStream inputStream = TransactionsApiApplicationTest.class
                .getResourceAsStream("/json/transaction-request-amount-too-large.json");
        String json = new String(Objects.requireNonNull(inputStream).readAllBytes());

        // When
        ResultActions resultActions = mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // Then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void givenTransactionAmountTooSmall_whenPostTransaction_thenBadRequest() throws Exception {
        // Given
        InputStream inputStream = TransactionsApiApplicationTest.class
                .getResourceAsStream("/json/transaction-request-amount-too-small.json");
        String json = new String(Objects.requireNonNull(inputStream).readAllBytes());

        // When
        ResultActions resultActions = mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // Then
        resultActions.andExpect(status().isBadRequest());
    }
}