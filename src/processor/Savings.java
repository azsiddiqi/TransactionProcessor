package processor;

public class Savings extends Account {

    protected boolean loyalCustomer;

    public double monthlyInterest() {
        if (loyalCustomer == true) {
            return (0.0045/12);
        }
        return (0.003/12);
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
