package processor;


import java.text.DecimalFormat;

public class AccountDatabase {

    private Account [] accounts;
    private int numAcct;

    public static final int NOT_FOUND = -1;
    public static final int INCREASE_ARRAY_CAPACITY = 4;

    public AccountDatabase(){
        this.accounts = new Account[INCREASE_ARRAY_CAPACITY];
        this.numAcct = 0;
    }

    public Account [] getAccounts() {
        return this.accounts;
    }

    public int getNumAcct() {
        return this.numAcct;
    }

    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private void grow() {
        Account[] increasedSize = new Account[accounts.length + INCREASE_ARRAY_CAPACITY];
        for (int i = 0; i < numAcct; i++){
            increasedSize[i] = accounts[i];
        }
        accounts = increasedSize;
    }

    public boolean open(Account account) {
        if (find(account) != NOT_FOUND && accounts[find(account)].closed == true){
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

    public boolean close(Account account) {
        int removedAcctIndex = find(account);
        if (removedAcctIndex == NOT_FOUND) {
            return false;
        }
        account.closed = true;
        account.balance = 0;
        if (account instanceof Savings) {
            Savings closedAccount = (Savings) account;
            closedAccount.loyalCustomer = false;
        }
        if (account instanceof MoneyMarket) {
            MoneyMarket closedAccount = (MoneyMarket) account;
            closedAccount.numberOfWithdrawl = 0;
        }
        return true;
    }

    public void deposit(Account account) {
        int findMatchingAccountIndex = find(account);
        if (findMatchingAccountIndex == -1) {
            System.out.println(account.holder + " " + account.getType() + " is not in the database.");
            return;
        }
        Account findMatchingAccount = accounts[findMatchingAccountIndex];
        findMatchingAccount.deposit(account.balance);
    }

    public boolean withdraw(Account account) {
        int findMatchingAccountIndex = find(account);
        if (findMatchingAccountIndex == -1) {
            System.out.println(account.holder + " " + account.getType() + " is not in the database.");
            return false;
        }
        Account findMatchingAccount = accounts[findMatchingAccountIndex];
        if (account.balance > findMatchingAccount.balance) {
            System.out.println("Withdraw - insufficient fund");
            return false;
        }
        findMatchingAccount.withdraw(account.balance);
        if (findMatchingAccount instanceof MoneyMarket) {
            MoneyMarket updateWithdrawls = (MoneyMarket) findMatchingAccount;
            updateWithdrawls.numberOfWithdrawl = updateWithdrawls.numberOfWithdrawl + 1;
        }
        return true;
    } //return false if insufficient fund

    public void print() {
        Account storeAccount;
        for (int i = 0; i < numAcct; i++) {
            System.out.println(accounts[i].toString());
        }
    }

    public void printByAccountType() {
        for (int i = 1; i < numAcct; i++) {
            Account saveAcct = accounts[i];
            int j = i - 1;
            while (j >= 0 && accounts[j].getType().compareTo(saveAcct.getType()) > 0) {
                accounts[j + 1] = accounts[j];
                j = j - 1;
            }
            accounts[j + 1] = saveAcct;
        }
        print();
    }

    public void printFeeAndInterest() {
        DecimalFormat PaddingZeroes = new DecimalFormat("#,##0.00");
        for (int i = 0; i < numAcct; i++){
            System.out.println(accounts[i].toString() + "::fee $" + PaddingZeroes.format(accounts[i].fee())
                    + "::monthly $" + PaddingZeroes.format(accounts[i].monthlyInterest()));
        }
    }
    public static void main(String[] args){

        Checking acc1 = new Checking(new Profile("K","S", new Date("01/02/2002")), 50);
        CollegeChecking acc2 = new CollegeChecking(new Profile("K","S", new Date("01/02/2002")), 100, 1);
        Savings acc3 = new Savings(new Profile("K","S", new Date("01/02/2002")), 200, 1);
        MoneyMarket acc4 = new MoneyMarket(new Profile("K","S", new Date("01/02/2002")), 210);


        AccountDatabase acd1 = new AccountDatabase();
        acd1.open(acc2);
        acd1.open(acc3);
        acd1.open(acc1);
        acd1.open(acc4);



        acd1.printByAccountType();
    }
}
