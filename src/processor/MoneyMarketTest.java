package processor;

import org.junit.Test;

import static org.junit.Assert.*;


public class MoneyMarketTest {

    @Test
    public void money_market_monthly_interest_for_balance_that_exceeds_2500_dollars() {
        //Test 1: Tests the monthly interest amount for a value of the balance that is above 2500
        MoneyMarket testAccount1 = new MoneyMarket(new Profile("Azaan","Siddiqi",new Date("1/01/2021")),
                3000.00);
        assertEquals((0.0095/12) * testAccount1.balance, testAccount1.monthlyInterest(), 0.00);
    }
    @Test
    public void money_market_monthly_interest_for_balance_that_is_2500_dollars() {
        //Test 2: Tests the monthly interest amount for a value of the balance that is 2500
        MoneyMarket testAccount2 = new MoneyMarket(new Profile("Azaan","Siddiqi",new Date("1/01/2021")),
                2500.00);
        assertEquals((0.0095/12) * testAccount2.balance, testAccount2.monthlyInterest(), 0.00);
    }
    @Test
    public void money_market_monthly_interest_for_balance_that_is_below_2500_dollars() {
        //Test 3: Tests the monthly interest amount for a value of the balance that is below 2500
        MoneyMarket testAccount3 = new MoneyMarket(new Profile("Azaan","Siddiqi",new Date("1/01/2021")),
                2000.00);
        assertEquals((0.008/12) * testAccount3.balance, testAccount3.monthlyInterest(), 0.00);
    }
}
