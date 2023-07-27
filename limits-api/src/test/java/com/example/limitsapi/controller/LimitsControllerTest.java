package com.example.limitsapi.controller;

import com.example.limitsapi.service.LimitsService;
import com.example.limitsapi.testing.MockTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(LimitsController.class)
class LimitsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LimitsService limitsService;

    @Test
    void givenValidAccountNumber_whenGetAccountLimits_thenOkResponseWithLimits() throws Exception {
        // Given
        var accountNumber = "999999999";
        var testAccountLimitsResponse = MockTestData.getAccountLimitsResponse();
        when(limitsService.getAccountLimits(accountNumber)).thenReturn(testAccountLimitsResponse);

        // When
        var response = mockMvc.perform(get("/limits?accountNumber={accountNumber}", accountNumber))
                .andReturn().getResponse();

        // Then
        InputStream inputStream = LimitsControllerTest.class.getResourceAsStream("/json/account-limits-200-response.json");
        String expectedJson = new String(Objects.requireNonNull(inputStream).readAllBytes());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(expectedJson, response.getContentAsString());
    }

    @Test
    void givenNoNumber_whenGetAccountLimits_thenBadRequest() throws Exception {
        // Given When
        var response = mockMvc.perform(get("/limits")).andReturn().getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}