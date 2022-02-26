package processor;

/**
 This class is a subclass of the Savings class extends all of its methods and applies conditions specific to a
 MoneyMarket account.
 @author Azaan Siddiqi, Karan Patel
 */
public class MoneyMarket extends Savings {

    protected int numberOfWithdrawl;

    /**

     @param holder
     @param balance
     */
    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance, 1);
    }

    /**

     @return
     */
    public double monthlyInterest() {
        if (loyalCustomer == true) {
            return (0.0095/12) * this.balance;
        }
        return (0.008/12) * this.balance;
    } //return the monthly interest

    /**

     @return
     */
    public double fee() {
        if (balance >= 2500 && this.numberOfWithdrawl <= 3) {
            return 0;
        }
        return 10;
    } //return the monthly fee

    /**

     @return
     */
    public String getType() {
        return "Money Market Savings";
    } //return the account type (class name)

    /**

     @return
     */
    @Override
    public String toString() {
        if (closed == true){
            this.numberOfWithdrawl = 0;
        }
        return super.toString() + "::withdrawl: " + this.numberOfWithdrawl;
    }
}
