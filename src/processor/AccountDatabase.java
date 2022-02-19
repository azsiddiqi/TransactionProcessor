package processor;


import java.text.DecimalFormat;

public class AccountDatabase {

    private Account [] accounts;
    private int numAcct;

    public static final int NOT_FOUND = -1;
    public static final int INCREASE_ARRAY_CAPACITY = 4;


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
    Account one = find(account);
    if (one != -1){
        one.deposit(account.balance);
    }
    }
    public boolean withdraw(Account account) { } //return false if insufficient fund

    public void print() {
        for (int i = 0; i < numAcct; i++){
            if (accounts[i].closed == true){
                System.out.println(accounts[i].toString() + "::CLOSED");
            } else {
                System.out.println(accounts[i].toString());
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
        for (int i = 0; i < numAcct; i++) {
            Account saveAcct = accounts[i];
            if (accounts[i].closed == true){
                System.out.println(accounts[i].toString() + "::CLOSED" + "::fee " + PaddingZeroes.format(saveAcct.fee())
                        + "::monthly " + PaddingZeroes.format(saveAcct.monthlyInterest())));
            }
            else{
                System.out.println(accounts[i].toString() + "::fee " + PaddingZeroes.format(saveAcct.fee()) + "::monthly " +
                        PaddingZeroes.format(saveAcct.monthlyInterest()));
            }
        }
    }
}
