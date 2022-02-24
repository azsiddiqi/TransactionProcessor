package processor;

public class CollegeChecking extends Checking {

    protected String campusName;//

    public CollegeChecking(Profile holder, double balance, int campusCode) {
        super(holder, balance);
        if (campusCode == 0){
            this.campusName = "NEW_BRUNSWICK";
        } else if (campusCode == 1) {
            this.campusName = "NEWARK";
        } else {
            this.campusName = "CAMDEN";
        }
    }

    public double monthlyInterest() {
        return (0.0025/12) * this.balance;
    } //return the monthly interest

    public double fee() {
        return 0;
    } //return the monthly fee

    public String getType(){
        return "College Checking";
    } //return the account type (class name)

    @Override
    public String toString() {
        return super.toString() + "::" + this.campusName;
    }
}
