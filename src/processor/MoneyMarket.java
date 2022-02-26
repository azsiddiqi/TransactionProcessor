package processor;

/**
 This class is a subclass of the Savings class extends all of its methods and applies conditions specific to a
 MoneyMarket account. It has a constructor based of a Profile object and account balance. Since the conditions of a money
 market account is that it is loyal the super from savings has a loyal customer code of 1. Along with this it has
 monthlyInterest, fee, and getType defined with proper values in respect to the guidelines of a money market
 account. Also, there is a toString method which returns information about the money market account as a string.
 @author Azaan Siddiqi, Karan Patel
 */
public class MoneyMarket extends Savings {

    protected int numberOfWithdrawl;
    public static final double LOYAL_MONEY_MARKET_MONTHLY_INTEREST_RATE = (0.0095/12);
    public static final double REGULAR_MONEY_MARKET_MONTHLY_INTEREST_RATE = (0.008/12);
    public static final double MONEY_MARKET_THRESHOLD = 2500;
    public static final double MONEY_MARKET_WITHDRAW_THRESHOLD = 3;
    public static final double MONEY_MARKET_FEE_THRESHOLD_MET = 0;
    public static final double MONEY_MARKET_FEE_THRESHOLD_NOT_MET = 0;

    /**
     Creates a MoneyMarket object based on a Profile object and a double denoting account balance.
     @param holder Profile for the holder of the account.
     @param balance balance of the account in USD.
     */
    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance, 1);
    }

    /**
     Calculates the amount of money accrued via interest per month based on account balance, money market monthly
     interest rate, and customer loyalty.
     @return the interest accrued in a month in USD based on college checking account rates.
     */
    public double monthlyInterest() {
        if (loyalCustomer == true) {
            return LOYAL_MONEY_MARKET_MONTHLY_INTEREST_RATE * this.balance;
        }
        return REGULAR_MONEY_MARKET_MONTHLY_INTEREST_RATE * this.balance;
    } //return the monthly interest

    /**
     Calculates the fee needed to be paid in order to have a money market account by using the savings account balance.
     @return the fee in USD to keep the money market account open.
     */
    public double fee() {
        if (balance >= MONEY_MARKET_THRESHOLD && this.numberOfWithdrawl <= MONEY_MARKET_WITHDRAW_THRESHOLD) {
            return MONEY_MARKET_FEE_THRESHOLD_MET;
        }
        return MONEY_MARKET_FEE_THRESHOLD_NOT_MET;
    } //return the monthly fee

    /**
     Returns the type of the money market account.
     @return a string denoting the type of the account.
     */
    public String getType() {
        return "Money Market Savings";
    } //return the account type (class name)

    /**
     Converts money market account information into a string.
     @return a string that follows the format of the Savings toString method and includes the number of withdrawls from
     the account.
     */
    @Override
    public String toString() {
        if (closed == true){
            this.numberOfWithdrawl = 0;
        }
        return super.toString() + "::withdrawl: " + this.numberOfWithdrawl;
    }
}
