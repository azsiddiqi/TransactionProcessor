package processor;

import java.text.DecimalFormat;

public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account secondAccount = (Account) obj;
            if (holder.equals(secondAccount.holder) && closed == secondAccount.closed){
                return true;
            }
        }
        return false;
    }//1232134235

    @Override
    public String toString() {
        DecimalFormat PaddingZeroes = new DecimalFormat("#.00");
        if (this.closed == true) {
            return this.getType() + "::" + holder.toString() + "::Balance " + PaddingZeroes.format(this.balance)
                    + "::CLOSED";
        }
        return this.getType() + "::" + holder.toString() + "::Balance " + PaddingZeroes.format(this.balance);
    }

    public void withdraw(double amount) {
        this.balance = this.balance - amount;
    }

    public void deposit(double amount) {
        this.balance = this.balance + amount;
    }

    public void updateBalance() {
        this.balance = this.balance - fee() + monthlyInterest();
    }

    public abstract double monthlyInterest(); //return the monthly interest
    public abstract double fee(); //return the monthly fee
    public abstract String getType(); //return the account type (class name)
}
