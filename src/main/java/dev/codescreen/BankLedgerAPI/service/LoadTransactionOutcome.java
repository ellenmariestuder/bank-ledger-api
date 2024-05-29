package dev.codescreen.BankLedgerAPI.service;
import dev.codescreen.BankLedgerAPI.model.RequestBody;

/**
 * <h2>LoadTransactionOutcome</h2>
* This class contains methods and properties for storing relevant
 * request and response data for load transaction requests. It 
 * implements the {@code response} method.
 */
public class LoadTransactionOutcome extends AbstractTransactionOutcome {
    public LoadTransactionOutcome(RequestBody requestData) {
        super(requestData);
    }
    /** 
     * <h3>approvedDenied()</h3>
     * For load requests, always approves.
     * @return result (String) "APPROVED"
    */
    @Override
    public String approvedDenied() {
        return "APPROVED";
    };
    /**
     * <h3>newBalance()</h3>
     * Calculates and returns updated balance-- adds transaction
     * 'amount' to current balance.
     * @param currentBalance user's current balance amount
     * @return newBalance (Double) balance amount after transaction
     */
    @Override
    public Double newBalance(Double currentBalance) {
        Double newBalance = currentBalance + amount;
        this.currentBalance = newBalance;
        return newBalance;
    };    
}