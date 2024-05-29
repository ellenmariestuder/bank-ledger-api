package dev.codescreen.BankLedgerAPI.service;

import dev.codescreen.BankLedgerAPI.model.RequestBody;

// TO-DO: add descriptions for getter/ setters
/**
 * <h2>AbstractTransactionOutcome</h2>
 * Defines methods and properties for storing relevant request
 * and response data for each transaction (load or authorization). 
 * Includes getters and setters for transaction data, and 
 * abstract methods for evaluating transaction requests. These
 * are implemented in the 
 * {@link dev.codescreen.BankLedgerAPI.service.LoadTransactionOutcome}
 * and 
 * {@link dev.codescreen.BankLedgerAPI.service.AuthTransactionOutcome}
 * child classes.
 * @param requestData The request data (cast from Json to {@link dev.codescreen.BankLedgerAPI.model.RequestBody} object)
 */
public abstract class AbstractTransactionOutcome {
  public String messageId;
  public String userId;
  public Double amount;
  public String currency;
  public String debitOrCredit;
  public Double currentBalance;

  public AbstractTransactionOutcome(RequestBody requestData) {
    setUserId(requestData.userId);
    setMessageId(requestData.messageId);
    setAmount(requestData.amount);
    setCurrency(requestData.currency);
    setDebitOrCredit(requestData.debitOrCredit);
    setCurrentBalance(0.0);
    }
  
  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    if (messageId == null || messageId.isEmpty()) {
      throw new IllegalArgumentException("Message ID cannot be null or empty");
    }
    this.messageId = messageId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    if (userId == null || userId.isEmpty()) {
        throw new IllegalArgumentException("User ID cannot be null or empty");
    }
    this.userId = userId;
}

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    if (amount == null || amount < 0.0) {
      throw new IllegalArgumentException("Amount cannot be null or negative");
    }
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    if (currency == null || currency.isEmpty()) {
      throw new IllegalArgumentException("Currency cannot be null or empty");
    }
    this.currency = currency;
  }

  public String getDebitOrCredit() {
    return debitOrCredit;
  }

  public void setDebitOrCredit(String debitOrCredit) {
    if (debitOrCredit == null || debitOrCredit.isEmpty()) {
      throw new IllegalArgumentException("DebitOrCredit cannot be null or empty");
    }
    this.debitOrCredit = debitOrCredit;
  }

  public Double getCurrentBalance() {
    return currentBalance;
  }

  public void setCurrentBalance(Double currentBalance) {
    if (currentBalance == null || currentBalance < 0.0) {
      throw new IllegalArgumentException("Current balance cannot be null or negative");
    }
    this.currentBalance = currentBalance;
  }

  /**
   * <h3>approvedDenied()</h3>
   * Evaluates whether transaction is approved or denied using 
   * the request data and user's current balance (returned by 
   * {@code getCurrentBalance()}).'load' requests are always 
   * approved; 'authorization' requests must check request amount 
   * against current balance.
   * @return result (String) "APPROVED" or "DENIED"
   */
  public abstract String approvedDenied();
  
  /**
   * <h3>newBalance()</h3>
   * Calculates user's new balance after transaction is processed.
   * Uses user's current balance, requested amount, transaction
   * type, and result of {@code approvedDenied()}.
   * @param currentBalance retrieved from user record in database
   * @return newBalance (Double)
   */
  public abstract Double newBalance(Double currentBalance);
    
}