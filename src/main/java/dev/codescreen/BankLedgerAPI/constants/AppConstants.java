package dev.codescreen.BankLedgerAPI.constants;

/**
 * <h2>AppConstants</h2>
 * Stores static data that is used elsewhere throughout the API.
 */
public class AppConstants {
    /**
     * <h3>databaseUri()</h3>
     * Assembles uri required for connecting to external database.
     * @return uri (String) uri for connecting to mongoDB
     */
    public static String databaseUri() {
        String USERNAME = getDBUsername();
        String PASSWORD = getDBPassword();
        final String mongoDBUri = String.format("mongodb+srv://%s:%s@cluster0.h86xqnm.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0", USERNAME, PASSWORD);
        return mongoDBUri;
    }
    /**
     * <h3>getDBUsername()</h3>
     * Retrieves username used for connecting to external database.
     * (Would typically  retrieve from password manager like AWS 
     * Secrets Manager) 
     * @return username (String) 
     */
    private static String getDBUsername() {
        String username = "ellenmstuder";
        return username;
    }
    
    /**
     * <h3>getDBPassword()</h3>
     * Retrieves password used for connecting to external database.
     * (Would typically  retrieve from password manager like AWS 
     * Secrets Manager) 
     * @return password (String) 
     */
    private static String getDBPassword() {
        String password = "mfn51hk97HRO4pXO";
        return password;
    }
}
