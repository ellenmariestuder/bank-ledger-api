package dev.codescreen.BankLedgerAPI.service;

import dev.codescreen.BankLedgerAPI.model.RequestBody;

/**
 * <h2>TransactionFactoryService</h2>
 * Class with single method ( {@code transactionType()} ) for 
 * evaluating, returing 
 * {@link dev.codescreen.BankLedgerAPI.service.AbstractTransactionOutcome} 
 * instace of either type 
 * {@link dev.codescreen.BankLedgerAPI.model.LoadResponseBody}
 * or {@link dev.codescreen.BankLedgerAPI.model.AuthResponseBody}
 * depending on the transaction request type (load/ auth).
 */
public class TransactionFactory {
    /**
    * <h3>transactionType()</h3>
     * Evaluates the transaction type of the incoming request via the 
     * { @code requestData,debitOrCredit() } value; returns appropriate
     * Transaction instance type 
     * ({@link dev.codescreen.BankLedgerAPI.service.LoadTransactionOutcome} 
     * or {@link dev.codescreen.BankLedgerAPI.service.AuthTransactionOutcome}).
     * @param requestData (RequestBody) transaction request data
     * @return transactionRequest (transactionRequest) instance of either 
     * 'LoadTransaction' or 'AuthTransaction'
     */
    public static AbstractTransactionOutcome transactionType(RequestBody requestData) {
        AbstractTransactionOutcome transactionRequest;
        String requestType = requestData.debitOrCredit;
        if (requestType.equals("CREDIT") ) {
            transactionRequest = new LoadTransactionOutcome(requestData);
        } else if (requestType.equals("DEBIT")) {
            transactionRequest = new AuthTransactionOutcome(requestData);
          }
        else {
            throw new IllegalArgumentException("Transaction type '" + requestType + "' not recognized.");
        }
        return transactionRequest;
    };
}