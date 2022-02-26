package processor;

import java.text.DecimalFormat;


/**
 This class creates an Account object based off of a Profile object and a double denoting the balance of an account.
 It also has an equals method to check to see if two accounts are the same and a toString method which returns the type
 of the account, the account holder, and the balance of the account. Along with this there are three abstract methods which
 extend to the Account class's subclasses that return the monthly interest, the monthly fee, and the type of the account.
 @author Azaan Siddiqi, Karan Patel
 */
public class AccountDatabase {

    private Account [] accounts;
    private int numAcct;

    public static final int NOT_FOUND = -1;
    public static final int INCREASE_ARRAY_CAPACITY = 4;
    public static final int MINIMUM_AMOUNT_FOR_MONEY_MARKET_LOYAL_CUSTOMER = 2500;


    /**
     *Creates an account database with an initial capacity of 4 and no accounts stored.
     */
    public AccountDatabase() {
        this.accounts = new Account[INCREASE_ARRAY_CAPACITY];
        this.numAcct = 0;
    }


    /**
     Returns array with all accounts in the database.
     @return array with all accounts in the database.
     */
    public Account [] getAccounts() {
        return this.accounts;
    }


    /**
     Returns number of accounts in the database.
     @return number of accounts in the database.
     */
    public int getNumAcct() {
        return this.numAcct;
    }


    /**
     Finds a specified account object within the database and returns its index within the array.
     @param account account that is being searched for
     @return index of the account within the array and -1 when the account is not found.
     */
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }


    /**
     Increases the size of the accounts array by 4.
     */
    private void grow() {
        Account[] increasedSize = new Account[accounts.length + INCREASE_ARRAY_CAPACITY];
        for (int i = 0; i < numAcct; i++) {
            increasedSize[i] = accounts[i];
        }
        accounts = increasedSize;
    }


    /**
     Opens a new account within the account database and also capable of reopening a previously closed account.
     * @param account the account that is being opened.
     * @return false if the account is being reopened and true if the account is being opened for the first time.
     */
    public boolean open(Account account) {
        if (find(account) != NOT_FOUND && accounts[find(account)].closed == true) {
            Account isInDatabase = accounts[find(account)];
            isInDatabase.balance = account.balance;
            if (isInDatabase instanceof CollegeChecking && account instanceof CollegeChecking) {
                CollegeChecking updateAccount = (CollegeChecking) isInDatabase;
                CollegeChecking newInformation = (CollegeChecking) account;
                updateAccount.campusName = newInformation.campusName;
            }
            if (isInDatabase instanceof Savings && account instanceof Savings) {
                Savings updateAccount = (Savings) isInDatabase;
                Savings newInformation = (Savings) account;
                updateAccount.loyalCustomer = newInformation.loyalCustomer;
            }
            isInDatabase.closed = false;
            return false;
        }
        if (numAcct == accounts.length) {
            grow();
        }
        accounts[numAcct] = account;
        numAcct++;
        return true;
    }


    /**
     Closes an account within th account database and resets balance and special conditions.
     @param account the account that is being closed.
     @return true if the account has been successfully closed.
     */
    public boolean close(Account account) {
        int removedAcctIndex = find(account);
        Account closeAccount = accounts[removedAcctIndex];
        closeAccount.closed = true;
        closeAccount.balance = 0;
        if (account instanceof Savings) {
            Savings closedAccount = (Savings) closeAccount;
            closedAccount.loyalCustomer = false;
        }
        if (account instanceof MoneyMarket) {
            MoneyMarket closedAccount = (MoneyMarket) closeAccount;
            closedAccount.numberOfWithdrawl = 0;
        }
        return true;
    }


    /**
     Deposits a specified balance into a specified account.
     @param account account that is being deposited into with the balance that is to be deposited.
     */
    public void deposit(Account account) {
        Account findMatchingAccount = accounts[find(account)];
        findMatchingAccount.deposit(account.balance);
    }


    /**
     Withdraws a specificed balance from a specified account.
     @param account accountthis is being withdrawn from with the balance that is being withdrawn.
     @return false if insufficient funds and true otherwise.
     */
    public boolean withdraw(Account account) {
        Account findMatchingAccount = accounts[find(account)];
        if (account.balance > findMatchingAccount.balance) {
            return false;
        }
        findMatchingAccount.withdraw(account.balance);
        if (findMatchingAccount instanceof MoneyMarket) {
            MoneyMarket updateWithdrawls = (MoneyMarket) findMatchingAccount;
            updateWithdrawls.numberOfWithdrawl = updateWithdrawls.numberOfWithdrawl + 1;
            if (updateWithdrawls.balance < MINIMUM_AMOUNT_FOR_MONEY_MARKET_LOYAL_CUSTOMER) {
                updateWithdrawls.loyalCustomer = false;
            }
        }
        return true;
    } //return false if insufficient fund


    /**
     Print out accounts within the database in the order that they are currently in.
     */
    public void print() {
        for (int i = 0; i < numAcct; i++) {
            System.out.println(accounts[i].toString());
        }
    }


    /**
     Print out the accounts in the database ordered by account type.
     */
    public void printByAccountType() {
        for (int i = 0; i < numAcct - 1; i++) {
            int minimumIndex = i;
            for (int j = i + 1; j < numAcct; j++) {
                if (accounts[j].getType().compareTo(accounts[minimumIndex].getType()) < 0) {
                    minimumIndex = j;
                }
            }
            Account swapPositions = accounts[minimumIndex];
            accounts[minimumIndex] = accounts[i];
            accounts[i] = swapPositions;
        }
        print();
    }


    /**
     Print out the accounts within the database and the respective fees and monthly interest accrued for each account.
     */
    public void printFeeAndInterest() {
        DecimalFormat PaddingZeroes = new DecimalFormat("#,##0.00");
        for (int i = 0; i < numAcct; i++) {
            System.out.println(accounts[i].toString() + "::fee $" + PaddingZeroes.format(accounts[i].fee())
                    + "::monthly interest $" + PaddingZeroes.format(accounts[i].monthlyInterest()));
        }
    }
}
