package processor;

public class CollegeChecking extends Checking {
    public double monthlyInterest(){
        return (0.0025/12);
    } //return the monthly interest

    public double fee(){
        return 0;
    } //return the monthly fee

    public String getType(){
        return "CollegeChecking";
    } //return the account type (class name)
}
