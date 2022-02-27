package processor;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountDatabaseTest {

    @Test
    public void open_checking_account() {
        AccountDatabase db1 = new AccountDatabase();
        assertTrue(db1.open(new Checking(new Profile("Azaan","Siddiqi", new Date("1/02/2022")), 200.00)));
    }

    @Test
    public void open_college_checking_account() {
        AccountDatabase db2 = new AccountDatabase();
        assertTrue(db2.open(new CollegeChecking(new Profile("Azaan","Siddiqi", new Date("1/02/2022")),200.00,1)));
    }

    @Test
    public void close() {
    }
}