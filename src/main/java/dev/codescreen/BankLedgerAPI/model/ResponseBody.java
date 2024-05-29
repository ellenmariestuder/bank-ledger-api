package dev.codescreen.BankLedgerAPI.model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import dev.codescreen.BankLedgerAPI.service.AbstractTransactionOutcome;
/**
 * <h2>ResponseBody</h2>
 * Stores data and method for returning the JSON response 
 * appropriate for the current transaction request.
 * @param transactionOutcome (AbstracttransactionOutcome) transaction request data
 */
public class ResponseBody {
    public String messageId;  
    public String userId;
    public String responseCode;  
    public Balance balance;

    public static class Balance {
        public Double amount;
        public String currency;
        public String debitOrCredit;
    }

    public ResponseBody(final AbstractTransactionOutcome transactionOutcome) {
        messageId    = transactionOutcome.getMessageId();
        userId       = transactionOutcome.getUserId();
        responseCode = transactionOutcome.approvedDenied();
        balance      = new Balance();
        balance.amount        = transactionOutcome.getCurrentBalance();
        balance.currency      = transactionOutcome.getCurrency();
        balance.debitOrCredit = transactionOutcome.getDebitOrCredit();
    }
    /**
     * <h3>toJson()</h3>
     * Converts instance properties into JSON object to be returned
     * as response data.
     * @return responseData (JSON Object) 
     */
    public String toJson() {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(this);
        } 
    catch (Exception e) {
        e.printStackTrace();
        return null;
        }
    }
}
