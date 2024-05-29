package dev.codescreen.BankLedgerAPI.constants;
import static org.junit.Assert.assertEquals;
// import org.junit.Test;
import org.junit.jupiter.api.Test;

public class AppConstantsTest {
    AppConstants constants = new AppConstants();
    @Test
    public void testDatabaseUri() {
        String expectedUri = "mongodb+srv://ellenmstuder:mfn51hk97HRO4pXO@cluster0.h86xqnm.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        String actualUri   = AppConstants.databaseUri(); 
        assertEquals(actualUri, expectedUri);

    }

    // @Test
    // public void testUserName() {}

    // @Test
    // public void testPassword() {}
}
