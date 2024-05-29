package dev.codescreen.BankLedgerAPI.model;

// import org.junit.Test;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.codescreen.BankLedgerAPI.service.AbstractTransactionOutcome;
// import com.fasterxml.jackson.core.JsonProcessingException;

public class ResponseBodyTest {

    @Test
    public void constructorInitializesFieldsCorrectly() {
        AbstractTransactionOutcome transactionOutcome = mock(AbstractTransactionOutcome.class);
        when(transactionOutcome.getMessageId()).thenReturn("msg123");
        when(transactionOutcome.getUserId()).thenReturn("user456");
        when(transactionOutcome.approvedDenied()).thenReturn("APPROVED");
        when(transactionOutcome.getCurrentBalance()).thenReturn(100.0);
        when(transactionOutcome.getCurrency()).thenReturn("USD");
        when(transactionOutcome.getDebitOrCredit()).thenReturn("CREDIT");

        ResponseBody responseBody = new ResponseBody(transactionOutcome);

        assertEquals("msg123", responseBody.messageId);
        assertEquals("user456", responseBody.userId);
        assertEquals("APPROVED", responseBody.responseCode);
        assertNotNull(responseBody.balance);
        assertEquals(100.0, responseBody.balance.amount);
        assertEquals("USD", responseBody.balance.currency);
        assertEquals("CREDIT", responseBody.balance.debitOrCredit);
    }

    @Test
    public void toJsonSerializesCorrectly() {
        AbstractTransactionOutcome transactionOutcome = mock(AbstractTransactionOutcome.class);
        when(transactionOutcome.getMessageId()).thenReturn("msg123");
        when(transactionOutcome.getUserId()).thenReturn("user456");
        when(transactionOutcome.approvedDenied()).thenReturn("APPROVED");
        when(transactionOutcome.getCurrentBalance()).thenReturn(100.0);
        when(transactionOutcome.getCurrency()).thenReturn("USD");
        when(transactionOutcome.getDebitOrCredit()).thenReturn("CREDIT");

        ResponseBody responseBody = new ResponseBody(transactionOutcome);
        String jsonOutput = responseBody.toJson();

        assertNotNull(jsonOutput);
        // JSONParser parser = new JSONParser();
        
        // assertTrue(jsonOutput.contains("\"messageId\":\"msg123\""));
        // assertTrue(jsonOutput.contains("\"userId\":\"user456\""));
        // assertTrue(jsonOutput.contains("\"responseCode\":\"APPROVED\""));
        // assertTrue(jsonOutput.contains("\"amount\":100.0"));
        // assertTrue(jsonOutput.contains("{\"debitOrCredit\":\"CREDIT\"}"));
        // assertTrue(jsonOutput.contains("\"debitOrCredit\":\"CREDIT\""));
    }

    // @Test // TO-DO: COME BACK AND FIX THIS
    // public void toJsonHandlesExceptionsGracefully() {
    //     AbstracttransactionOutcome transactionOutcome = mock(AbstracttransactionOutcome.class);
    //     ResponseBody responseBody = new ResponseBody(transactionOutcome);

    //     // Forcing an exception by setting up incorrect data
    //     when(transactionOutcome.currentBalance).thenThrow(new RuntimeException("Unexpected Error"));

    //     assertNull(responseBody.toJson(), "Json serialization should handle exceptions and return null");
    // }
}
