package processor;

public class Savings extends Account {

    protected boolean loyalCustomer;

    public Savings(Profile holder, double balance, int loyalCustomerCode) {
        super(holder, balance);
        if (loyalCustomerCode == 0) {
            this.loyalCustomer = false;
        } else {
            this.loyalCustomer = true;
        }
    }
    public double monthlyInterest() {
        if (loyalCustomer == true) {
            return (0.0045/12) * this.balance;
        }
        return (0.003/12) * this.balance;
    } //return the monthly interest

    public double fee() {
        if (balance >= 300) {
            return 0;
        }
        return 6;
    } //return the monthly fee

    public String getType() {
        return "Savings";
    } //return the account type (class name)

    @Override
    public String toString() {
        if (loyalCustomer == true) {
            return super.toString() + "::Loyal";
        }
        return super.toString();
    }
}
