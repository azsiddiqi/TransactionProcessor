package processor;

import static org.junit.Assert.*;


public class AccountDatabaseTest {


    @org.junit.Test
    public void open_checking_account() {
        //Test 1: Tests the opening of a checking account
        AccountDatabase db1 = new AccountDatabase();
        Checking addAccount1 = new Checking(new Profile("Azaan","Siddiqi", new Date("1/02/2022")),
                200.00);
        assertTrue(db1.open(addAccount1));
    }


    @org.junit.Test
    public void open_college_checking_account() {
        //Test 2: Tests the opening of a college checking account
        AccountDatabase db2 = new AccountDatabase();
        CollegeChecking addAccount2 = new CollegeChecking(new Profile("Karan","Patel",
                new Date("7/1/2020")), 1000.00, 0);
        assertTrue(db2.open(addAccount2));
    }


    @org.junit.Test
    public void open_savings_account() {
        //Test 3: Tests the opening of a savings account
        AccountDatabase db3 = new AccountDatabase();
        Savings addAccount3 = new Savings(new Profile("John","Doe", new Date("3/20/1990")), 2000,
                1);
        assertTrue(db3.open(addAccount3));
    }


    @org.junit.Test
    public void open_money_market_account() {
        //Test 4: Tests the opening of a money market account
        AccountDatabase db4 = new AccountDatabase();
        MoneyMarket addAccount4 = new MoneyMarket(new Profile("Jane","Moe", new Date("10/10/1999")),
                1975);
        assertTrue(db4.open(addAccount4));
    }


    @org.junit.Test
    public void open_then_close_checking_account() {
        //Test 5: Tests the opening, then closing of a checking account
        AccountDatabase db5 = new AccountDatabase();
        Checking addAccount5 = new Checking(new Profile("Azaan","Siddiqi", new Date("1/02/2022")),
                200.00);
        db5.open(addAccount5);
        assertTrue(db5.close(addAccount5));
    }


    @org.junit.Test
    public void open_then_close_college_checking_account() {
        //Test 6: Tests the opening, then closing of a college checking account
        AccountDatabase db6 = new AccountDatabase();
        CollegeChecking addAccount6 = new CollegeChecking(new Profile("Karan","Patel",
                new Date("7/1/2020")), 1000.00, 0);
        db6.open(addAccount6);
        assertTrue(db6.close(addAccount6));
    }


    @org.junit.Test
    public void open_then_close_savings_account() {
        //Test 7: Tests the opening, then closing of a savings account
        AccountDatabase db7 = new AccountDatabase();
        Savings addAccount7 = new Savings(new Profile("John","Doe", new Date("3/20/1990")), 2000,
                1);
        db7.open(addAccount7);
        assertTrue(db7.close(addAccount7));
    }


    @org.junit.Test
    public void open_then_close_money_market_account() {
        //Test 8: Tests the opening, then closing of a money market account
        AccountDatabase db8 = new AccountDatabase();
        MoneyMarket addAccount8 = new MoneyMarket(new Profile("Jane","Moe", new Date("10/10/1999")),
                1975);
        db8.open(addAccount8);
        assertTrue(db8.close(addAccount8));
    }


    @org.junit.Test
    public void open_then_close_then_reopen_checking_account() {
        //Test 9: Tests the opening, then closing, then reopening of a checking account
        AccountDatabase db9 = new AccountDatabase();
        Checking addAccount9 = new Checking(new Profile("Azaan","Siddiqi", new Date("1/02/2022")),
                200.00);
        db9.open(addAccount9);
        db9.close(addAccount9);
        assertFalse(db9.open(addAccount9));
    }


    @org.junit.Test
    public void open_then_close_then_reopen_college_checking_account() {
        //Test 10: Tests the opening, then closing, then reopening of a college checking account
        AccountDatabase db10 = new AccountDatabase();
        CollegeChecking addAccount10 = new CollegeChecking(new Profile("Karan","Patel",
                new Date("7/1/2020")), 1000.00, 0);
        db10.open(addAccount10);
        db10.close(addAccount10);
        assertFalse(db10.open(addAccount10));
    }


    @org.junit.Test
    public void open_then_close_then_reopen_savings_account() {
        //Test 11: Tests the opening, then closing, then reopening of a savings account
        AccountDatabase db11 = new AccountDatabase();
        Savings addAccount11 = new Savings(new Profile("John","Doe", new Date("3/20/1990")), 2000,
                1);
        db11.open(addAccount11);
        db11.close(addAccount11);
        assertFalse(db11.open(addAccount11));
    }


    @org.junit.Test
    public void open_then_close_then_reopen_money_market_account() {
        //Test 12: Tests the opening, then closing, then reopening of a money market account
        AccountDatabase db12 = new AccountDatabase();
        MoneyMarket addAccount12 = new MoneyMarket(new Profile("Jane","Moe", new Date("10/10/1999")),
                1975);
        db12.open(addAccount12);
        db12.close(addAccount12);
        assertFalse(db12.open(addAccount12));
    }


    @org.junit.Test
    public void close_checking_account_not_in_database() {
        //Test 13: Tests the closing of a checking account that doesn't exist in the database
        AccountDatabase db13 = new AccountDatabase();
        Checking closeAccount13 = new Checking(new Profile("Azaan","Siddiqi", new Date("1/02/2022")),
                200.00);
        assertFalse(db13.close(closeAccount13));
    }


    @org.junit.Test
    public void close_college_checking_account_not_in_database() {
        //Test 14: Tests the closing of a college checking account that doesn't exist in the database
        AccountDatabase db14 = new AccountDatabase();
        CollegeChecking closeAccount14 = new CollegeChecking(new Profile("Karan","Patel",
                new Date("7/1/2020")), 1000.00, 0);
        assertFalse(db14.close(closeAccount14));
    }


    @org.junit.Test
    public void close_savings_account_not_in_database() {
        //Test 15: Tests the closing of a savings account that doesn't exist in the database
        AccountDatabase db15 = new AccountDatabase();
        Savings closeAccount15 = new Savings(new Profile("John","Doe", new Date("3/20/1990")), 2000,
                1);
        assertFalse(db15.close(closeAccount15));
    }


    @org.junit.Test
    public void close_money_market_account() {
        //Test 16: Tests the closing of a money market account that doesn't exist in the database
        AccountDatabase db16 = new AccountDatabase();
        MoneyMarket closeAccount16 = new MoneyMarket(new Profile("Jane","Moe", new Date("10/10/1999")),
                1975);
        assertFalse(db16.close(closeAccount16));
    }

}