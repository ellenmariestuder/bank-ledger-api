package dev.codescreen.BankLedgerAPI.service;

import dev.codescreen.BankLedgerAPI.model.RequestBody;

/**
 * <h2>AuthTransactionOutcome</h2>
 * Contains methods and properties for storing relevant request
 * and response data for authorization transaction requests.
 * Implements the {@code approvedDenied()} and {@code newBalance()} 
 * methods. Extends 
 * {@link dev.codescreen.BankLedgerAPI.service.AbstractTransactionOutcome}
 */
public class AuthTransactionOutcome extends AbstractTransactionOutcome {
    public AuthTransactionOutcome(RequestBody requestData) {
        super(requestData);
    }
    /** 
     * <h3>approvedDenied()</h3>
     * Evaluates whether Authorization transaction (DEBIT) is approved 
     * or denied. Approved if requested debit amount is less than the
     * current balance amount, otherwise Denied.
     * @return result (String) "APPROVED" or "DENIED"
    */
    @Override
    public String approvedDenied() {
        String result = "APPROVED";
        if (amount > currentBalance) {result = "DENIED";}
        return result;
    };
    /**
     * <h3>newBalance()</h3>
     * Calculates and returns updated balance-- if requested debit amount
     * is less than the current balance, the balance is reduced by the
     * requested amount. Otherwise transaction is declined and balance is
     * unchanged.
     * @param currentBalance user's current balance amount
     * @return newBalance (Double) balance amount after transaction
     */
    @Override
    public Double newBalance(Double currentBalance) {
        Double newBalance = currentBalance;
        if (amount <= currentBalance) { newBalance -= amount; }
        return newBalance;
    };
}