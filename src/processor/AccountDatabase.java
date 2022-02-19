package processor;


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
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i].equals(account)) {
                return false;
            }
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
        for (int j = removedAcctIndex + 1; j < numAcct; j++) {
                accounts[j - 1] = accounts[j];
        }
        accounts[numAcct - 1] = null;
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
            System.out.println(accounts[i].toString());
        }
    }
    public void printByAccountType() { }
    public void printFeeAndInterest() { }
}
