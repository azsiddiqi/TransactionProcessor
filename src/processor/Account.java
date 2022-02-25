package processor;

import java.text.DecimalFormat;

/**
 This class creates an Account object based off of a Profile object and a double denoting the balance of an account.
 It also has an equals method to check to see if two accounts are the same and a toString method which returns the type
 of the account, the account holder, and the balance of the account. Along with this there are three abstract methods which
 extend to the Account classes subclasses that return the monthly interest, the monthly fee, and the type of the account.
 @author Azaan Siddiqi, Karan Patel
 */
public abstract class Account {
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    /**
     Creates a date object based on a profile and an account balance double.
     @param holder Profile of the account holder.
     @param balance Balance of the account in USD.
     */
    public Account(Profile holder, double balance){
        this.holder = holder;
        this.closed = false;
        this.balance = balance;
    }

    /**
     Compares the holder and type of two account objects in order to determine if that are the same account.
     @param obj the specified account object to be compared to the original account object.
     @return true if the holder object and the type of both account objects are equal and false if they aren't equal.
     */
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

    /**
     Converts account information into a string.
     @return A string containing the account type, holder, balance, and whether the account is closed or not.
     */
    @Override
    public String toString() {
        DecimalFormat PaddingZeroes = new DecimalFormat("#,##0.00");
        if (this.closed == true) {
            return this.getType() + "::" + holder.toString() + "::Balance $" + PaddingZeroes.format(this.balance)
                    + "::CLOSED";
        }
        return this.getType() + "::" + holder.toString() + "::Balance $" + PaddingZeroes.format(this.balance);
    }

    /**
     Subtracts a specified amount from the account balance to signify withdrawing from an account.
     @param amount the amount in USD to withdraw from the account.
     */
    public void withdraw(double amount) {
        this.balance = this.balance - amount;
    }

    /**
     Adds a specified amount to the account balance to signify depositing into an account.
     @param amount the amount in USD to deposit into the account.
     */
    public void deposit(double amount) {
        this.balance = this.balance + amount;
    }

    /**
     Updates the balance of the account by taking into consideration the account fees and monthly interest accrued.
     */
    public void updateBalance() {
        this.balance = this.balance - fee() + monthlyInterest();
    }

    public abstract double monthlyInterest(); //return the monthly interest
    public abstract double fee(); //return the monthly fee
    public abstract String getType(); //return the account type (class name)
}
