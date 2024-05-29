package dev.codescreen.BankLedgerAPI.service;
import static org.junit.Assert.assertTrue;
// import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// import org.junit.Test;
import org.junit.jupiter.api.Test;

import dev.codescreen.BankLedgerAPI.model.AuthResponseBody;
import dev.codescreen.BankLedgerAPI.model.LoadResponseBody;
import dev.codescreen.BankLedgerAPI.model.ResponseBody;

public class ResponseFactoryTest {

    @Test
    public void testTransactionType_Credit() {
        // Arrange
        AbstractTransactionOutcome details = mock(AbstractTransactionOutcome.class);
        when(details.getDebitOrCredit()).thenReturn("CREDIT");

        // Act
        ResponseBody result = ResponseFactory.transactionType(details);

        // Assert
        assertTrue(result instanceof LoadResponseBody);
    }

    @Test
    public void testTransactionType_Debit() {
        // Arrange
        AbstractTransactionOutcome details = mock(AbstractTransactionOutcome.class);
        when(details.getDebitOrCredit()).thenReturn("DEBIT");

        // Act
        ResponseBody result = ResponseFactory.transactionType(details);

        // Assert
        assertTrue(result instanceof AuthResponseBody);
    }

    @Test
    public void testTransactionType_Invalid() {
        // Arrange
        AbstractTransactionOutcome details = mock(AbstractTransactionOutcome.class);
        when(details.getDebitOrCredit()).thenReturn("INVALID");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            ResponseFactory.transactionType(details);
        });
    }
}
