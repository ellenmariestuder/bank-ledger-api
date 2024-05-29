package dev.codescreen.BankLedgerAPI;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.codescreen.BankLedgerAPI.controller.RestController;
import dev.codescreen.BankLedgerAPI.model.RequestBody;
import io.javalin.Javalin;
import java.sql.Timestamp;
/**
* <h2>Bank Ledger REST API</h2>
* The BankLedgerAPI is a REST API that allows users to deposit and 
* withdraw funds from a (theoretical) bank account. It uses the 
* Event Source pattern to maintain an immutable record of all
* transactions, such that the balance at any given time could be 
* reconstructed by 'playing back' the transaction history as it 
* exists in the ledger.
* <p>
* User and transaction data is stored in a MongoDB NoSQL document
* database. The database is structured such that the main collection 
* --Users-- stores user documents. Each user document consists of the
* userID, currentBalance, and a Ledger collection. Transaction events 
* are stored within the Ledger collection, in accordance with the 
* Event Source pattern design and requirements.
*
* @author  Ellen Studer
* @version 1.0
* @since   2024-05-06
*/

public class BankLedgerAPI {
    /**
     * <h3>main()</h3>
     * Entrypoint to the BankLedgerAPI. REST endpoints and their handling 
     * logic are declared here. Running this method initiates a server 
     * instance and facilitates sending and receiving transaction request 
     * data via the API endpoints.
     */
    public static void main(String[] args) {
      Javalin app = Javalin.create().start(7777);
      
      String timestamp = new Timestamp(System.currentTimeMillis()).toString();
      app.get("/ping", ctx -> ctx.result(timestamp));
      
      app.post("/authorization", ctx -> {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody requestObject = objectMapper.readValue(ctx.body(), RequestBody.class);
        String result = RestController.process(requestObject);
        ctx.result(result);
      });
      
      app.post("/load", ctx -> {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody requestObject = objectMapper.readValue(ctx.body(), RequestBody.class);
        String result = RestController.process(requestObject);
        ctx.result(result);
      });
    }
  }
