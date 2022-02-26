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
        Account closeAccount = accounts[removedAcctIndex];
        closeAccount.closed = true;
        closeAccount.balance = 0;
        if (account instanceof Savings) {
            Savings closedAccount = (Savings) closeAccount;
            closedAccount.loyalCustomer = false;
        }
        if (account instanceof MoneyMarket) {
            MoneyMarket closedAccount = (MoneyMarket) closeAccount;
            closedAccount.loyalCustomer = false;
            closedAccount.numberOfWithdrawl = 0;
        }
        return true;
    }

    public void deposit(Account account) {
        Account findMatchingAccount = accounts[find(account)];
        findMatchingAccount.deposit(account.balance);
    }

    public boolean withdraw(Account account) {
        Account findMatchingAccount = accounts[find(account)];
        findMatchingAccount.withdraw(account.balance);
        if (findMatchingAccount instanceof MoneyMarket) {
            MoneyMarket updateWithdrawls = (MoneyMarket) findMatchingAccount;
            updateWithdrawls.numberOfWithdrawl = updateWithdrawls.numberOfWithdrawl + 1;
            if (updateWithdrawls.balance < 2500) {
                updateWithdrawls.loyalCustomer = false;
            }
        }
        return true;
    } //return false if insufficient fund

    public void print() {
        for (int i = 0; i < numAcct; i++) {
            System.out.println(accounts[i].toString());
        }
    }

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
        System.out.println("\n*list of accounts by account type.");
        print();
        System.out.println("*end of list.\n");
    }

    public void printFeeAndInterest() {
        DecimalFormat PaddingZeroes = new DecimalFormat("#,##0.00");
        System.out.println("\n*list of accounts with fee and monthly interest");
        for (int i = 0; i < numAcct; i++){
            System.out.println(accounts[i].toString() + "::fee $" + PaddingZeroes.format(accounts[i].fee())
                    + "::monthly interest $" + PaddingZeroes.format(accounts[i].monthlyInterest()));
        }
        System.out.println("*end of list.\n");
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
