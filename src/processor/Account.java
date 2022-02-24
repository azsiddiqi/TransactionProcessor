package processor;

import java.text.DecimalFormat;

public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    public Account(Profile holder, double balance){
        this.holder = holder;
        this.closed = false;
        this.balance = balance;//
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account secondAccount = (Account) obj;
            if (holder.equals(secondAccount.holder) && this.getType().equals(secondAccount.getType())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        DecimalFormat PaddingZeroes = new DecimalFormat("#,##0.00");
        if (this.closed == true) {
            return this.getType() + "::" + holder.toString() + "::Balance $" + PaddingZeroes.format(this.balance)
                    + "::CLOSED";
        }
        return this.getType() + "::" + holder.toString() + "::Balance $" + PaddingZeroes.format(this.balance);
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
