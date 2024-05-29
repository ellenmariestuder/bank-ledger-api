package dev.codescreen.BankLedgerAPI.service;

/**
 * <h2>DBConnectionInterface</h2>
 * Declares all methods needed to perform the database interactions
 * required for this API.
 */
public interface DatabaseInterface {
    /**
     * <h3>open()</h3>
     * Establishes connection to the external database.
     */
    public void open();
    
    /**
     * <h3>close()</h3>
     * Terminates connection to the external database.
     */
    public void close();
    
    /**
     * <h3>createUserRecord()</h3>
     * Creates and inserts new record for user if their userId isn't
     * already in the database.
     */
    public String createUserRecord();
    
    /**
     * <h3>appendEventRecord()</h3>
     * Adds new event record to the Ledger collection for that user.
     */
    public Object appendEventRecord();
    
    /**
     * <h3>updateUserBalance()</h3>
     * Updates 'balance' value on a user's record. (This is separate 
     * from the user's Ledger, which cannot be updated after a record is 
     * added.)
     */
    public Object updateUserBalance();

}