package processor;

public class MoneyMarket extends Savings {
    public double monthlyInterest(){
        return (0.008/12);
    } //return the monthly interest

    public double fee(){
        if (balance >= 2500){
            return 0;
        }
        return 10;
    } //return the monthly fee

    public String getType(){
        return "MoneyMarket";
    } //return the account type (class name)
}
