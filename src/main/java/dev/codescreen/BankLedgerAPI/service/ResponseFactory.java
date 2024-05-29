package dev.codescreen.BankLedgerAPI.service;

import dev.codescreen.BankLedgerAPI.model.AuthResponseBody;
import dev.codescreen.BankLedgerAPI.model.LoadResponseBody;
import dev.codescreen.BankLedgerAPI.model.ResponseBody;

/**
 * <h2>ResponseFactoryService</h2>
 * Class with single method ( {@code transactionType()} ) for 
 * evaluating, returing ResponseBody instace of either type 
 * {@link dev.codescreen.BankLedgerAPI.model.LoadResponseBody}
 * or {@link dev.codescreen.BankLedgerAPI.model.AuthResponseBody}
 * depending on the transaction request type (load/ auth).
 */
public class ResponseFactory {
    /**
     * <h3>transactionType()</h3>
     * Evaluates transaction type of current request via the 
     * {@code DebitCredit} value; returns appropriate Responsebody instance 
     * type ({@link dev.codescreen.BankLedgerAPI.model.LoadResponseBody} 
     * or {@link dev.codescreen.BankLedgerAPI.model.AuthResponseBody}).
     * @param transactionOutcome (AbstracttransactionOutcome) request data
     * @return responseBody (ResponseBody) Instance of either 'LoadResponse' or 'AuthResponse'.
     */
    public static ResponseBody transactionType(AbstractTransactionOutcome transactionOutcome) {
        ResponseBody responseBody;
        
        String requestType = transactionOutcome.getDebitOrCredit();
        if (requestType.equals("CREDIT") ) {
            responseBody = new LoadResponseBody(transactionOutcome);
        } else if (requestType.equals("DEBIT")) {
            responseBody = new AuthResponseBody(transactionOutcome);
          }
        else {
            throw new IllegalArgumentException("Transaction type '" + requestType + "' not recognized.");
        }
        return responseBody;
    };
}