package dev.codescreen.BankLedgerAPI.model;
import dev.codescreen.BankLedgerAPI.service.AbstractTransactionOutcome;

public class AuthResponseBody extends ResponseBody {
/**
 * <h2>AuthResponseBody</h2>
 * Stores data and method for returning the JSON response for 
 * Authorization transactions.
 * @param transactionOutcome (AbstracttransactionOutcome) transaction request data
 */
    public AuthResponseBody(final AbstractTransactionOutcome transactionOutcome) {
        super(transactionOutcome);
    } 
}
