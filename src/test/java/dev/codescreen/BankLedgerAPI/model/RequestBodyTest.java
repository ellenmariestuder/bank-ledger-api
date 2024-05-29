package dev.codescreen.BankLedgerAPI.model;

// import org.junit.Test;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class RequestBodyTest {

    @Test
    public void testUnpackNestedCorrectlySetsFields() {
        RequestBody requestBody = new RequestBody();
        Map<String, Object> transactionAmount = new HashMap<>();
        transactionAmount.put("amount", "100.0");
        transactionAmount.put("currency", "USD");
        transactionAmount.put("debitOrCredit", "CREDIT");

        requestBody.unpackNested(transactionAmount);

        assertEquals(100.0, requestBody.amount);
        assertEquals("USD", requestBody.currency);
        assertEquals("CREDIT", requestBody.debitOrCredit);
    }
    @Test
    public void testUnpackNestedWithIncorrectTypes() {
        RequestBody requestBody = new RequestBody();
        Map<String, Object> transactionAmount = new HashMap<>();
        transactionAmount.put("amount", "not-a-number");
        transactionAmount.put("currency", 123);  // incorrect type
        transactionAmount.put("debitOrCredit", "DEBIT");

        Exception exception = assertThrows(NumberFormatException.class, () -> {
            requestBody.unpackNested(transactionAmount);
        });

        assertTrue(exception.getMessage().contains("For input string"));
    }
}

// TO-DO: Additional tests-- 
// null checks
// partial data
// boundary values  
