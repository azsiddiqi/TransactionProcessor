package processor;

/**
 This class is a subclass of the Account class thus extends all of its methods and applies conditions specific to a
 Checking account. It has a constructor based of a Profile object and Account balance. Along with this it has
 monthlyInterest, fee, and getType defined with proper values in respect to the guidelines of a checking account.
 @author Azaan Siddiqi, Karan Patel
 */
public class Checking extends Account {

    public static final double CHECKING_MONTHLY_INTEREST_RATE = (0.001/12);
    public static final double CHECKING_FEE_BALANCE_ABOVE_1000 = 0;
    public static final double CHECKING_FEE_BALANCE_BELOW_1000 = 25;

    /**
     Creates a checking account object based of holder Profile object and a specified account balance.
     @param holder Profile of the account holder.
     @param balance Balance of the account in USD.
     */
    public Checking(Profile holder, double balance) {
        super(holder, balance);
    }

    /**
     Calculates the amount of money accrued via interest per month based on account balance and checking monthly interest
     rate.
     @return the interest accrued in a month in USD based on checking account rates.
     */
    public double monthlyInterest() {
        return CHECKING_MONTHLY_INTEREST_RATE * this.balance;
    } //return the monthly interest

    /**
     Calculates the fee needed to be paid in order to have a checking account by using the checking account balance.
     @return the fee in USD to keep the checking account open.
     */
    public double fee() {
        if (balance >= 1000) {
            return CHECKING_FEE_BALANCE_ABOVE_1000;//
        }
        return CHECKING_FEE_BALANCE_BELOW_1000;
    } //return the monthly fee

    /**
     Returns the type of the Checking account.
     @return a string denoting the type of the account.
     */
    public String getType() {
        return "Checking";
    } //return the account type (class name)

}
