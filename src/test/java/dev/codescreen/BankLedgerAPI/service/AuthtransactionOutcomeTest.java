package dev.codescreen.BankLedgerAPI.service;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.codescreen.BankLedgerAPI.model.RequestBody;

public class AuthtransactionOutcomeTest {
    public static AuthTransactionOutcome authTransaction;
    public static RequestBody requestData;
    public static Object requestJson;

    @BeforeAll
    public static void setup() throws IOException {
        String jsonString = "{\"messageId\": \"msg123\", \"userId\": \"user456\", \"currentBalance\": \"5.0\", \"transactionAmount\": {\"amount\": \"100.0\", \"currency\": \"USD\", \"debitOrCredit\": \"DEBIT\"}}";
        ObjectMapper objectMapper = new ObjectMapper();
        requestData = objectMapper.readValue(jsonString, RequestBody.class);
        authTransaction = new AuthTransactionOutcome(requestData);
    }

    @Test
    public void testApprovedDenied() throws IOException {
        String result = authTransaction.approvedDenied();
        assertEquals("APPROVED", result, "The approvedDenied method should return 'APPROVED'");
    }

    @Test
    public void testNewBalance() {

    }
}
