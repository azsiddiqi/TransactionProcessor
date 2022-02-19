package processor;

public class MoneyMarket extends Savings {

    public double monthlyInterest() {
        if (loyalCustomer == true) {
            return (0.0095/12);
        }
        return (0.008/12);
    } //return the monthly interest

    public double fee() {
        if (balance >= 2500) {
            return 0;
        }
        return 10;
    } //return the monthly fee

    public String getType() {
        return "Money Market";
    } //return the account type (class name)
}
