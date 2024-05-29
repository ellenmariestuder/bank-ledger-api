package dev.codescreen.BankLedgerAPI.model;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * <h2>RequestBody</h2>
 * Provides structure for casting raw JSON objects to usable
 * POJOs.
 */
public class RequestBody {
    public String messageId;  
    public String userId;  
    public Double amount;
    public String currency;
    public String debitOrCredit;
  
    @JsonProperty("transactionAmount")
    public void unpackNested(final Map<String, Object> transactionAmount) {
      final String amountStr   = (String) transactionAmount.get("amount");
      this.amount        = (Double) Double.valueOf(amountStr);
      this.currency      = (String) transactionAmount.get("currency");
      this.debitOrCredit = (String) transactionAmount.get("debitOrCredit");
    }
}
