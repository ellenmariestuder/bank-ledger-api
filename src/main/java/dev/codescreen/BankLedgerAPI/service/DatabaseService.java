package dev.codescreen.BankLedgerAPI.service;
import dev.codescreen.BankLedgerAPI.constants.AppConstants;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * <h2>DBConnectionService</h2>
 * Handles all functionality related to interacting with external 
 * database.
 */
public class DatabaseService implements DatabaseInterface{
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static AbstractTransactionOutcome transactionOutcome;

    public DatabaseService(AbstractTransactionOutcome transactionInstance) {
        transactionOutcome = transactionInstance;
    }

    /**
     * <h3>open()</h3>
     * Establishes connection to the external database.
     */
    @Override
    public void open() {
        String uri  = AppConstants.databaseUri();
        mongoClient = MongoClients.create(uri);
        database    = mongoClient.getDatabase("bank_ledger");
    }
    
    /**
     * <h3>close()</h3>
     * Terminates connection to the external database.
     */
    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
    
    /**
     * <h3>createUserRecord()</h3>
     * Creates new user record with fields: userId (String), balance 
     * (Double), ledgerEventRecords (Array/ Collection). Adds new record
     * to users collection.
     * @return id (ObjectId) newly generated uniqueId for user
     */
    @Override
    public String createUserRecord() {
        MongoCollection<Document> usersCollection = database.getCollection("users");
        Document document = new Document("userId", transactionOutcome.getUserId());
        document.append("balance", 0.0);
        document.append("ledgerEventRecords", new ArrayList<>());
        InsertOneResult result = usersCollection.insertOne(document);
        System.out.println("New user document acknowledged: " + result.wasAcknowledged());
        String id = result.getInsertedId().asObjectId().getValue().toHexString();
        return id;
    };
    
    /**
     * <h3>appendEventRecord()</h3>
     * Creates record containing transaction request data(time stamp, 
     * message id, transaction type, amount); Appends it to user's 
     * ledgerEventRecords collection.
     */
    @Override
    public Object appendEventRecord() {
        MongoCollection<Document> usersCollection = database.getCollection("users");
        
        //get ID you'll have to send to 
        Document queryResult = usersCollection.find(Filters.eq("userId", transactionOutcome.getUserId())).first();
        if (queryResult == null) {
            createUserRecord();
        }
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Document document   = new Document("messageId", transactionOutcome.getMessageId());
        document.append("timeStamp", timestamp);
        document.append("transactionType", transactionOutcome.getDebitOrCredit());
        document.append("amount", transactionOutcome.getAmount());
        
        try {
            UpdateResult result = usersCollection.updateOne(
                new BasicDBObject(new BasicDBObject("userId", transactionOutcome.getUserId())),
                new BasicDBObject("$push", new BasicDBObject("ledgerEventRecords", document))
                );
                System.out.println(result.wasAcknowledged());
            } catch (MongoException me) {
                System.err.println("Unable to update due to an error: " + me);
            }
            return null;
        };
        
        //TO-DO: CREATE NEW METHOD JUST FOR RETRIEVING CURRENT BALANCE AND 
        // UPDATING transactionOutcome WITH IT
    /**
     * <h3>updateUserBalance()</h3>
     * Retrieves user's current balance, runs {@code newBalance()} method to 
     * determine updated amount, updates user's 'currentBalance' to the new
     * balance. (This is separate from the user's Ledger, which cannot be
     * updated after a record is added.)
     * @return updatedBalance (Double) user's new 'current' balance after the 
     * transaction
     */
    @Override
    public Double updateUserBalance() {
        MongoCollection<Document> usersCollection = database.getCollection("users");
        Document queryResult  = usersCollection.find(Filters.eq("userId", transactionOutcome.getUserId())).first();
        Double currentBalance = queryResult.getDouble("balance");
        Double updatedBalance = transactionOutcome.newBalance(currentBalance);
        System.out.println("THIS IS THE NEW BALANCE: " + updatedBalance);
        try {
            UpdateResult result = usersCollection.updateOne(
                new BasicDBObject(new BasicDBObject("userId", transactionOutcome.getUserId())),
                new BasicDBObject("$set", new BasicDBObject("balance", updatedBalance))
            );
            System.out.println(result.wasAcknowledged());
        } catch (MongoException me) {
            System.err.println("Unable to update due to an error: " + me);
        }
        return updatedBalance;
    }
}