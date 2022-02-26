package processor;
import java.util.Scanner;
import java.lang.NumberFormatException;
public class BankTeller {

    private AccountDatabase allAccts;

    public static final int VALID_NUMBER_OF_INFORMATION_FOR_OPENING_CHECKING_OR_MONEYMARKET = 6;
    public static final int VALID_NUMBER_OF_INFORMATION_FOR_OPENING_COLLEGECHECKING_OR_SAVINGS = 7;
    public static final int VALID_NUMBER_OF_INFORMATION_FOR_CLOSING_ACCOUNT = 5;
    public static final int NOT_FOUND = -1;

    public BankTeller() {
        this.allAccts = new AccountDatabase();
    }

    private void updateAndDisplayBalances() {
        for (int i = 0; i < allAccts.getNumAcct(); i++) {
            allAccts.getAccounts()[i].updateBalance();
        }
        System.out.println("\n*list of accounts with updated balance");
        allAccts.print();
        System.out.println("*end of list.\n");
    }

    private int findAccount(Account account) {
        for (int i = 0; i < allAccts.getNumAcct(); i++) {
            if (allAccts.getAccounts()[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    private boolean checkIfSameAccounts(Account account) {
        for (int i = 0; i < allAccts.getNumAcct(); i++){
            if (findAccount(account) != NOT_FOUND && allAccts.getAccounts()[findAccount(account)].closed == false) {
                return true;
            }
            if (allAccts.getAccounts()[i].holder.equals(account.holder) && ((allAccts.getAccounts()[i].getType().equals("Checking") &&
                    account.getType().equals("College Checking")) || (allAccts.getAccounts()[i].getType().equals("College Checking")
                    && account.getType().equals("Checking")))) {
                return true;
            }
        }
        return false;
    }

    private void openAccount(String[] splitInformation) {
        if (splitInformation[1].equals("C") || splitInformation[1].equals("MM")) {
            if (splitInformation.length < VALID_NUMBER_OF_INFORMATION_FOR_OPENING_CHECKING_OR_MONEYMARKET) {
                System.out.println("Missing data for opening an account.");
                return;
            }
        } else if (splitInformation[1].equals("CC") || splitInformation[1].equals("S")) {
            if (splitInformation.length < VALID_NUMBER_OF_INFORMATION_FOR_OPENING_COLLEGECHECKING_OR_SAVINGS) {
                System.out.println("Missing data for opening an account.");
                return;
            }
        }
        Date dob = new Date(splitInformation[4]);
        Date today = new Date();
        if (dob.isValid() == false || dob.compareTo(today) >= 0) {
            System.out.println("Date of birth invalid.");
            return;
        }
        Profile holder = new Profile(splitInformation[2], splitInformation[3], dob);
        double balance = 0;
        try {
            balance = Double.parseDouble(splitInformation[5]);
        } catch (NumberFormatException invalidBalance) {
            System.out.println("Not a valid amount.");
            return;
        }
        if (balance <= 0) {
            System.out.println("Initial deposit cannot be 0 or negative.");
            return;
        }
        if (splitInformation[1].equals("C")) {
            Checking addAccount = new Checking(holder, balance);
            boolean checkIfValid = checkIfSameAccounts(addAccount);
            if (checkIfValid == true) {
                System.out.println(addAccount.holder.toString() + " same account(type) is in the database.");
                return;
            }
            checkIfValid = allAccts.open(addAccount);
            if (checkIfValid == false) {
                System.out.println("Account reopened.");
            } else {
                System.out.println("Account opened.");
            }
        } else if (splitInformation[1].equals("CC")) {
            int campusCode = Integer.parseInt(splitInformation[6]);
            if (!(campusCode == 0 || campusCode == 1 || campusCode == 2)) {
                System.out.println("Invalid campus code.");
                return;
            }
            CollegeChecking addAccount = new CollegeChecking(holder, balance, campusCode);
            boolean checkIfValid = checkIfSameAccounts(addAccount);
            if (checkIfValid == true) {
                System.out.println(addAccount.holder.toString() + " same account(type) is in the database.");
                return;
            }
            checkIfValid = allAccts.open(addAccount);
            if (checkIfValid == false) {
                System.out.println("Account reopened.");
            } else {
                System.out.println("Account opened.");
            }
        } else if (splitInformation[1].equals("S")) {
            int loyalCustomerCode = Integer.parseInt(splitInformation[6]);
            Savings addAccount = new Savings(holder, balance, loyalCustomerCode);
            boolean checkIfValid = checkIfSameAccounts(addAccount);
            if (checkIfValid == true) {
                System.out.println(addAccount.holder.toString() + " same account(type) is in the database.");
                return;
            }
            checkIfValid = allAccts.open(addAccount);
            if (checkIfValid == false) {
                System.out.println("Account reopened.");
            } else {
                System.out.println("Account opened.");
            }
        } else if (splitInformation[1].equals("MM")) {
            MoneyMarket addAccount = new MoneyMarket(holder, balance);
            if (balance < 2500 && findAccount(addAccount) == NOT_FOUND) {
                System.out.println("Minimum of $2500 to open a MoneyMarket account.");
                return;
            }
            if (balance < 2500) {
                addAccount.loyalCustomer = false;
            }
            boolean checkIfValid = checkIfSameAccounts(addAccount);
            if (checkIfValid == true) {
                System.out.println(addAccount.holder.toString() + " same account(type) is in the database.");
                return;
            }
            checkIfValid = allAccts.open(addAccount);
            if (checkIfValid == false) {
                System.out.println("Account reopened.");
            } else {
                System.out.println("Account opened.");
            }
        } else {
            System.out.println("Invalid command!");
            return;
        }
    }

    private void closeAccount(String[] splitInformation) {
        if (splitInformation.length < VALID_NUMBER_OF_INFORMATION_FOR_CLOSING_ACCOUNT) {
            System.out.println("Missing data for closing an account.");
            return;
        }
        if (splitInformation[1].equals("C")) {
            Checking closeAccount = new Checking(new Profile(splitInformation[2], splitInformation[3], new Date(splitInformation[4])),
                    0);
            if (allAccts.getAccounts()[findAccount(closeAccount)].closed == true) {
                System.out.println("Account is closed already.");
                return;
            }
            allAccts.close(closeAccount);
        } else if (splitInformation[1].equals("CC")) {
            CollegeChecking closeAccount = new CollegeChecking(new Profile(splitInformation[2], splitInformation[3], new Date(splitInformation[4])),
                    0, 0);
            if (allAccts.getAccounts()[findAccount(closeAccount)].closed == true) {
                System.out.println("Account is closed already.");
                return;
            }
            allAccts.close(closeAccount);
        } else if (splitInformation[1].equals("S")) {
            Savings closeAccount = new Savings(new Profile(splitInformation[2], splitInformation[3], new Date(splitInformation[4])),
                    0, 0);
            if (allAccts.getAccounts()[findAccount(closeAccount)].closed == true) {
                System.out.println("Account is closed already.");
                return;
            }
            allAccts.close(closeAccount);
        } else if (splitInformation[1].equals("MM")) {
            MoneyMarket closeAccount = new MoneyMarket(new Profile(splitInformation[2], splitInformation[3], new Date(splitInformation[4])),
                    0);
            if (allAccts.getAccounts()[findAccount(closeAccount)].closed == true) {
                System.out.println("Account is closed already.");
                return;
            }
            allAccts.close(closeAccount);
        }
        System.out.println("Account closed.");
    }
    private void depositIntoAccount(String[] splitInformation) {
        double balance = 0;
        try {
            balance = Double.parseDouble(splitInformation[5]);
        } catch (NumberFormatException invalidBalance) {
            System.out.println("Not a valid amount.");
            return;
        }
        Profile holder = new Profile(splitInformation[2], splitInformation[3], new Date(splitInformation[4]));
        if (balance <= 0) {
            System.out.println("Deposit - amount cannot be 0 or negative.");
            return;
        }
        if (splitInformation[1].equals("C")) {
            Checking increaseBalance = new Checking(holder, balance);
            int findMatchingAccountIndex = findAccount(increaseBalance);
            if (findMatchingAccountIndex == -1) {
                System.out.println(increaseBalance.holder + " " + increaseBalance.getType() + " is not in the database.");
                return;
            }
            allAccts.deposit(increaseBalance);
        } else if (splitInformation[1].equals("CC")) {
            CollegeChecking increaseBalance = new CollegeChecking(holder, balance, 0);
            int findMatchingAccountIndex = findAccount(increaseBalance);
            if (findMatchingAccountIndex == -1) {
                System.out.println(increaseBalance.holder + " " + increaseBalance.getType() + " is not in the database.");
                return;
            }
            allAccts.deposit(increaseBalance);
        } else if (splitInformation[1].equals("S")) {
            Savings increaseBalance = new Savings(holder, balance, 0);
            int findMatchingAccountIndex = findAccount(increaseBalance);
            if (findMatchingAccountIndex == -1) {
                System.out.println(increaseBalance.holder + " " + increaseBalance.getType() + " is not in the database.");
                return;
            }
            allAccts.deposit(increaseBalance);
        } else if (splitInformation[1].equals("MM")) {
            MoneyMarket increaseBalance = new MoneyMarket(holder, balance);
            int findMatchingAccountIndex = findAccount(increaseBalance);
            if (findMatchingAccountIndex == -1) {
                System.out.println(increaseBalance.holder + " Money Market is not in the database.");
                return;
            }
            allAccts.deposit(increaseBalance);
        }
        System.out.println("Deposit - balance updated.");
    }

    private void withdrawFromAccount(String[] splitInformation) {
        double balance = 0;
        try {
            balance = Double.parseDouble(splitInformation[5]);
        } catch (NumberFormatException invalidBalance) {
            System.out.println("Not a valid amount.");
            return;
        }
        Profile holder = new Profile(splitInformation[2], splitInformation[3], new Date(splitInformation[4]));
        if (balance <= 0) {
            System.out.println("Withdraw - amount cannot be 0 or negative.");
            return;
        }
        if (splitInformation[1].equals("C")) {
            Checking decreaseBalance = new Checking(holder, balance);
            int findMatchingAccountIndex = findAccount(decreaseBalance);
            if (findMatchingAccountIndex == -1) {
                System.out.println(decreaseBalance.holder + " " + decreaseBalance.getType() + " is not in the database.");
                return;
            }
            if (decreaseBalance.balance > allAccts.getAccounts()[findAccount(decreaseBalance)].balance) {
                System.out.println("Withdraw - insufficient fund.");
                return;
            }
            allAccts.withdraw(decreaseBalance);
        } else if (splitInformation[1].equals("CC")) {
            CollegeChecking decreaseBalance = new CollegeChecking(holder, balance, 0);
            int findMatchingAccountIndex = findAccount(decreaseBalance);
            if (findMatchingAccountIndex == -1) {
                System.out.println(decreaseBalance.holder + " " + decreaseBalance.getType() + " is not in the database.");
                return;
            }
            if (decreaseBalance.balance > allAccts.getAccounts()[findAccount(decreaseBalance)].balance) {
                System.out.println("Withdraw - insufficient fund.");
                return;
            }
            allAccts.withdraw(decreaseBalance);
        } else if (splitInformation[1].equals("S")) {
            Savings decreaseBalance = new Savings(holder, balance, 0);
            int findMatchingAccountIndex = findAccount(decreaseBalance);
            if (findMatchingAccountIndex == -1) {
                System.out.println(decreaseBalance.holder + " " + decreaseBalance.getType() + " is not in the database.");
                return;
            }
            if (decreaseBalance.balance > allAccts.getAccounts()[findAccount(decreaseBalance)].balance) {
                System.out.println("Withdraw - insufficient fund.");
                return;
            }
            allAccts.withdraw(decreaseBalance);
        } else if (splitInformation[1].equals("MM")) {
            MoneyMarket decreaseBalance = new MoneyMarket(holder, balance);
            int findMatchingAccountIndex = findAccount(decreaseBalance);
            if (findMatchingAccountIndex == -1) {
                System.out.println(decreaseBalance.holder + " Money Market is not in the database.");
                return;
            }
            if (decreaseBalance.balance > allAccts.getAccounts()[findAccount(decreaseBalance)].balance) {
                System.out.println("Withdraw - insufficient fund.");
                return;
            }
            allAccts.withdraw(decreaseBalance);
        }
        System.out.println("Withdraw - balance updated.");
    }
    public void run() {
        System.out.println("Bank Teller is running.");
        Scanner readStandardInput = new Scanner(System.in);
        while (readStandardInput.hasNext()) {
            String storeLine = readStandardInput.nextLine();
            String[] splitInformation = storeLine.split("\\s+");
            if (splitInformation[0].equals("O")) {
                openAccount(splitInformation);
            } else if (splitInformation[0].equals("C")) {
                closeAccount(splitInformation);
            } else if (splitInformation[0].equals("D")) {
                depositIntoAccount(splitInformation);
            } else if (splitInformation[0].equals("W")) {
                withdrawFromAccount(splitInformation);
            } else if (splitInformation[0].equals("P")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    continue;
                }
                System.out.println("\n*list of accounts in the database*");
                allAccts.print();
                System.out.println("*end of list*\n");
            } else if (splitInformation[0].equals("PT")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    continue;
                }
                allAccts.printByAccountType();
            } else if (splitInformation[0].equals("PI")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    continue;
                }
                allAccts.printFeeAndInterest();
            } else if (splitInformation[0].equals("UB")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    continue;
                }
                updateAndDisplayBalances();
            } else if (splitInformation[0].equals("Q")) {
                System.out.println("Bank Teller is terminated.");
                break;
            } else if (splitInformation[0].isEmpty()) {
                continue;
            } else {
                System.out.println("Invalid command!");
            }
        }
        readStandardInput.close();
    }
}
