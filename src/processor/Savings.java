package processor;

/**
 This class is a subclass of the Account class thus extends all of its methods and applies conditions specific to a
 Savings account. It has a constructor based of a Profile object, Account balance, and a loyalCustomerCode which denotes
 whether a customer is loyal or not. Along with this it has monthlyInterest, fee, and getType defined with proper values
 in respect to the guidelines of a Savings account. Also, there is a toString method which returns information about the
 Savings account as a string.
 @author Azaan Siddiqi, Karan Patel
 */
public class Savings extends Account {

    protected boolean loyalCustomer;
    public static final double LOYAL_SAVINGS_MONTHLY_INTEREST_RATE = (0.0045/12);
    public static final double REGULAR_SAVINGS_MONTHLY_INTEREST_RATE = (0.003/12);
    public static final double SAVINGS_FEE_THRESHOLD = 300;
    public static final double SAVINGS_FEE_ABOVE_300 = 0;
    public static final double SAVINGS_FEE_UNDER_300 = 6;

    /**
     Creates a Savings object based on a Profile object, a double denoting account balance, and an integer denoting
     whether the holder is a loyal customer or not.
     @param holder Profile of the account holder.
     @param balance Savings account balance in USD.
     @param loyalCustomerCode integer which if it is 0 means the customer isn't loyal, otherwise customer is loyal.
     */
    public Savings(Profile holder, double balance, int loyalCustomerCode) {
        super(holder, balance);
        if (loyalCustomerCode == 0) {
            this.loyalCustomer = false;
        } else {
            this.loyalCustomer = true;
        }
    }

    /**
     Calculates the amount of money accrued via interest per month based on account balance and savings monthly interest
     rate.
     @return the interest accrued in a month in USD based on savings account rates.
     */
    public double monthlyInterest() {
        if (loyalCustomer == true) {
            return LOYAL_SAVINGS_MONTHLY_INTEREST_RATE * this.balance;
        }
        return  REGULAR_SAVINGS_MONTHLY_INTEREST_RATE * this.balance;
    } //return the monthly interest

    /**
     Calculates the fee needed to be paid in order to have a savings account by using the savings account balance.
     @return the fee in USD to keep the savings account open.
     */
    public double fee() {
        if (balance >= SAVINGS_FEE_THRESHOLD) {
            return SAVINGS_FEE_ABOVE_300;
        }
        return SAVINGS_FEE_UNDER_300;
    } //return the monthly fee

    /**
     Returns the type of the savings account.
     @return a string denoting the type of the account.
     */
    public String getType() {
        return "Savings";
    } //return the account type (class name)

    /**
     Converts savings account information into a string.
     @return a string that follows the format of the Account and includes whether the customer is loyal or not.
     */
    @Override
    public String toString() {
        if (loyalCustomer == true) {
            return super.toString() + "::Loyal";
        }
        return super.toString();
    }
}
