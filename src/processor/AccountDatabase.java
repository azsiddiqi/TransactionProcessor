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
    public boolean open(Account account) { }
    public boolean close(Account account) { }
    public void deposit(Account account) { }
    public boolean withdraw(Account account) { } //return false if insufficient fund
    public void print() {
        for (int i = 0; i < numAcct; i++){
            System.out.println(accounts[i].toString());
        }
    }
    public void printByAccountType() { }
    public void printFeeAndInterest() { }
}
