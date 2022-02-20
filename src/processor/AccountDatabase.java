package processor;


import java.text.DecimalFormat;

public class AccountDatabase {

    private Account [] accounts;
    private int numAcct;

    public static final int NOT_FOUND = -1;
    public static final int INCREASE_ARRAY_CAPACITY = 4;


    public Account [] getAccounts() {
        return this.accounts;
    }

    public int getNumAcct() {
        return this.numAcct;
    }

    private int find(Account account) {
        for (int i = 0;i < numAcct; i++) {
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
        account.closed = false;
        if (find(account) != NOT_FOUND){
            return true;
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
        return true;
    }

    public void deposit(Account account) {
        int findMatchingAccountIndex = find(account);
        if (findMatchingAccountIndex == -1 || account.balance <= 0) {
            return;
        }
        Account findMatchingAccount = accounts[findMatchingAccountIndex];
        findMatchingAccount.deposit(account.balance);

    }

    public boolean withdraw(Account account) {
        int findMatchingAccountIndex = find(account);
        if (findMatchingAccountIndex == -1) {
            return false;
        }
        Account findMatchingAccount = accounts[findMatchingAccountIndex];
        if (account.balance > findMatchingAccount.balance) {
            return false;
        }
        findMatchingAccount.withdraw(account.balance);
        return true;
    } //return false if insufficient fund

    public void print() {
        Account storeAccount;
        for (int i = 0; i < numAcct; i++){
            if (accounts[i] instanceof Checking) {
                storeAccount = (Checking) accounts[i];
                System.out.println(storeAccount.toString());
            } else if (accounts[i] instanceof CollegeChecking) {
                storeAccount = (CollegeChecking) accounts[i];
                System.out.println(storeAccount.toString());
            } else if (accounts[i] instanceof Savings) {
                storeAccount = (Savings) accounts[i];
                System.out.println(storeAccount.toString());
            } else if (accounts[i] instanceof MoneyMarket) {
                storeAccount = (MoneyMarket) accounts[i];
                System.out.println(storeAccount.toString());
            }
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
        DecimalFormat PaddingZeroes = new DecimalFormat("#.00");
        Account storeAccount;
        for (int i = 0; i < numAcct; i++){
            if (accounts[i] instanceof Checking) {
                storeAccount = (Checking) accounts[i];
                System.out.println(storeAccount.toString() + "::fee " + PaddingZeroes.format(storeAccount.fee())
                        + "::monthly " + PaddingZeroes.format(storeAccount.monthlyInterest()));
            } else if (accounts[i] instanceof CollegeChecking) {
                storeAccount = (CollegeChecking) accounts[i];
                System.out.println(storeAccount.toString() + "::fee " + PaddingZeroes.format(storeAccount.fee())
                        + "::monthly " + PaddingZeroes.format(storeAccount.monthlyInterest()));
            } else if (accounts[i] instanceof Savings) {
                storeAccount = (Savings) accounts[i];
                System.out.println(storeAccount.toString() + "::fee " + PaddingZeroes.format(storeAccount.fee())
                        + "::monthly " + PaddingZeroes.format(storeAccount.monthlyInterest()));
            } else if (accounts[i] instanceof MoneyMarket) {
                storeAccount = (MoneyMarket) accounts[i];
                System.out.println(storeAccount.toString() + "::fee " + PaddingZeroes.format(storeAccount.fee())
                        + "::monthly " + PaddingZeroes.format(storeAccount.monthlyInterest()));
            }
        }
    }
}
