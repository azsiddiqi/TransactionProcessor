package processor;

public class Checking extends Account {

    public double monthlyInterest() {
        return (0.001/12);
    } //return the monthly interest

    public double fee() {
        if (balance >= 1000) {
            return 0;
        }
        return 25;
    } //return the monthly fee

    public String getType() {
        return "Checking";
    } //return the account type (class name)

}
