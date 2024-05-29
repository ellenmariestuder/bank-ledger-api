package dev.codescreen.BankLedgerAPI.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.codescreen.BankLedgerAPI.service.AbstractTransactionOutcome;
/**
 * <h2>LoadResponseBody</h2>
 * Stores data and method for returning the JSON response for 
 * Load transactions.
 * @param transactionOutcome (AbstracttransactionOutcome) transaction request data
 */
public class LoadResponseBody extends ResponseBody {
    @JsonIgnore
    public String responseCode;

    public LoadResponseBody(final AbstractTransactionOutcome transactionOutcome) {
        super(transactionOutcome);
    }
}
