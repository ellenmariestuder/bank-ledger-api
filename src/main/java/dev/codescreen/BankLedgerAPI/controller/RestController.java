package dev.codescreen.BankLedgerAPI.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import dev.codescreen.BankLedgerAPI.service.AbstractTransactionOutcome;
import dev.codescreen.BankLedgerAPI.service.TransactionFactory;
import dev.codescreen.BankLedgerAPI.model.ResponseBody;
import dev.codescreen.BankLedgerAPI.model.RequestBody;
import dev.codescreen.BankLedgerAPI.service.DatabaseService;
import dev.codescreen.BankLedgerAPI.service.ResponseFactory;
/**
 * <h2>RestController</h2>
 * RestController contains a single method {@code process()} to
 * manage transaction handling logic using a Template pattern.
 */
public class RestController {

    /**
     * <h3>process()</h3>
     * Implements steps for processing load and authorization
     * requests. Sends event data to the Ledger collection
     * approves/ denies transactions, calculates new balances, 
     * generates and returns response data.
     * 
     * @param requestJson The raw Json request data
     * @return status code + loadResponse or authResponse _or_ ServerError
     * @throws JsonProcessingException
     */
    public static String process(Object requestJson) throws JsonProcessingException {
        
        // determine transaction type, return appropriate transactionOutcome class
        AbstractTransactionOutcome transactionOutcome;             // declare class that will hold transaction info
        RequestBody requestData = (RequestBody) requestJson;       // cast request JSON to POJO
        transactionOutcome      = TransactionFactory.transactionType(requestData); // return appropriate 'detail' instance (load/ auth)
        
        // connect to database, perform CRUD operations
        DatabaseService database = new DatabaseService(transactionOutcome);
        database.open();
        database.appendEventRecord();
        database.updateUserBalance();
        database.close();
        
        // convert transaction data to json object and return
        ResponseBody responseData = ResponseFactory.transactionType(transactionOutcome); // return appropriate 'response' instance (load/ auth)
        String responseJson       = responseData.toJson();         // cast to JSON
        return responseJson;                                       // return response data
    };
}
