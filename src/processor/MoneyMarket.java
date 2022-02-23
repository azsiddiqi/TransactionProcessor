package processor;

public class MoneyMarket extends Savings {

    protected int numberOfWithdrawl;

    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance, 1);
    }

    public void checkBalance() {
        if (this.balance < 2500) {
            this.loyalCustomer = false;
        }
    }

    public double monthlyInterest() {
        if (loyalCustomer == true) {
            return (0.0095/12);
        }
        return (0.008/12);
    } //return the monthly interest

    public double fee() {
        if (balance >= 2500 && this.numberOfWithdrawl <= 3) {
            return 0;
        }
        return 10;
    } //return the monthly fee

    public String getType() {
        return "Money Market Savings";
    } //return the account type (class name)

    @Override
    public String toString() {
        if (closed == true){
            this.numberOfWithdrawl = 0;
        }
        return super.toString() + "::withdrawl: " + this.numberOfWithdrawl;
    }
}
