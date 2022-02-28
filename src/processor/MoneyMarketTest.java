package processor;

import static org.junit.Assert.*;


public class MoneyMarketTest {

    public static final double LOYAL_MONEY_MARKET_MONTHLY_INTEREST_RATE = (0.0095/12);
    public static final double REGULAR_MONEY_MARKET_MONTHLY_INTEREST_RATE = (0.008/12);


    @org.junit.Test
    public void money_market_monthly_interest_for_balance_that_exceeds_2500_dollars() {
        //Test 1: Tests the monthly interest amount for a value of the balance that is above 2500
        MoneyMarket testAccount1 = new MoneyMarket(new Profile("Azaan","Siddiqi",new Date("1/01/2021")),
                3000.00);
        assertEquals(LOYAL_MONEY_MARKET_MONTHLY_INTEREST_RATE * testAccount1.balance,
                testAccount1.monthlyInterest(), 0.00);
    }


    @org.junit.Test
    public void money_market_monthly_interest_for_balance_that_is_2500_dollars() {
        //Test 2: Tests the monthly interest amount for a value of the balance that is 2500
        MoneyMarket testAccount2 = new MoneyMarket(new Profile("Karan","Patel",new Date("7/1/2020")),
                2500.00);
        assertEquals(LOYAL_MONEY_MARKET_MONTHLY_INTEREST_RATE * testAccount2.balance,
                testAccount2.monthlyInterest(), 0.00);
    }


    @org.junit.Test
    public void money_market_monthly_interest_for_balance_that_is_below_2500_dollars() {
        //Test 3: Tests the monthly interest amount for a value of the balance that is below 2500
        MoneyMarket testAccount3 = new MoneyMarket(new Profile("John","Doe",new Date("3/20/1990")),
                2000.00);
        assertEquals(REGULAR_MONEY_MARKET_MONTHLY_INTEREST_RATE * testAccount3.balance,
                testAccount3.monthlyInterest(), 0.00);
    }
}
