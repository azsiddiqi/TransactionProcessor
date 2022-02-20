package processor;

public class CollegeChecking extends Checking {

    protected String campusName;

    public double monthlyInterest() {
        return (0.0025/12);
    } //return the monthly interest

    public double fee() {
        return 0;
    } //return the monthly fee

    public String getType(){
        return "College Checking";
    } //return the account type (class name)

    @Override
    public String toString() {
        return super.toString() + this.campusName;
    }
}
