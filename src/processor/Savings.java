package processor;

public class Savings extends Account {
    public double monthlyInterest(){
        return (0.003/12);
    } //return the monthly interest

    public double fee(){
        if (balance >= 300){
            return 0;
        }
        return 6;
    } //return the monthly fee

    public String getType(){
        return "Savings";
    } //return the account type (class name)
}
