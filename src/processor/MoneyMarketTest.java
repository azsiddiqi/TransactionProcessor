package processor;

import org.junit.Test;

import static org.junit.Assert.*;


public class MoneyMarketTest {

    @Test
    public void money_market_balance_exceeds_2500_dollars() {
        //Test 1: Tests that the value for balance that exceeds 2500
        MoneyMarket testAccount1 = new MoneyMarket(new Profile("Azaan","Siddiqi",new Date("1/01/2021")),
                3000.00);
        assertEquals((0.0095/12) * testAccount1.balance, testAccount1.monthlyInterest(), 0.00);
    }
    @Test
    public void money_market_balance_is_2500_dollars() {
        //Test 2: Tests that the value for balance that is 2500
        MoneyMarket testAccount2 = new MoneyMarket(new Profile("Azaan","Siddiqi",new Date("1/01/2021")),
                2500.00);
        assertEquals((0.0095/12) * testAccount2.balance, testAccount2.monthlyInterest(), 0.00);
    }
    @Test
    public void money_market_balance_below_2500_dollars() {
        //Test 3: Tests that the value for balance that is below 2500
        MoneyMarket testAccount3 = new MoneyMarket(new Profile("Azaan","Siddiqi",new Date("1/01/2021")),
                2000.00);
        assertEquals((0.008/12) * testAccount3.balance, testAccount3.monthlyInterest(), 0.00);
    }
}
