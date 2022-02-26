package processor;
import java.util.Scanner;
import java.lang.NumberFormatException;


/**
 This class contains and modifies an AccountDatabase object based on the commands and information obtained from standard
 input. These commands may request to open an account, which may be done after checking that the inputted information is
 valid. These commands may also request to close an account, deposit into an account, withdraw from an account, and
 print all the accounts in various ways. There is also a BankTeller constructor for initializing an AccountDatabase
 object, as well as various helper methods.
 @author Karan Patel, Azaan Siddiqi
 */
public class BankTeller {

    private AccountDatabase allAccts;

    public static final int VALID_NUMBER_OF_INFORMATION_FOR_OPENING_CHECKING_OR_MONEY_MARKET = 6;
    public static final int VALID_NUMBER_OF_INFORMATION_FOR_OPENING_COLLEGE_CHECKING_OR_SAVINGS = 7;
    public static final int VALID_NUMBER_OF_INFORMATION_FOR_CLOSING_ACCOUNT = 5;
    public static final int NOT_FOUND = -1;
    public static final int MONEY_MARKET_WAIVED_THRESHOLD = 2500;
    public static final int NEW_BRUNSWICK_CAMPUS_CODE = 0;
    public static final int NEWARK_CAMPUS_CODE = 1;
    public static final int CAMDEN_CAMPUS_CODE = 2;


    /**
     Constructor for a BankTeller object that instantiates an AccountDatabase object. This AccountDatabase object will
     keep track of all the accounts in the database.
     */
    public BankTeller() {
        this.allAccts = new AccountDatabase();
    }


    /**
     Finds the account object that is passed into the method in the AccountDatabase object array and returns its index
     within the array if it exists, or -1 if it doesn't exist.
     @param account the account object that is being searched for in the AccountDatabase object array.
     @return the index of the account object in the array if it exists, or -1 if it doesn't exist in the array.
     */
    private int accountFinder(Account account) {
        for (int i = 0; i < allAccts.getNumAcct(); i++) {
            if (allAccts.getAccounts()[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }


    /**
     Checks if an open account of the same type and Profile as the one passed into the method exists in the database.
     Also checks to see if the account passed into the method is a checking account and a similar college checking
     account already exists in the database, and vice versa.
     @param account account object to be compared against the database to see if another matching account already
     exists.
     @return true if another matching account already exists, false otherwise.
     */
    private boolean sameAccountsChecker(Account account) {
        for (int i = 0; i < allAccts.getNumAcct(); i++) {
            if (accountFinder(account) != NOT_FOUND && allAccts.getAccounts()[accountFinder(account)].closed == false) {
                System.out.println(account.holder.toString() + " same account(type) is in the database.");
                return true;
            }
            if (allAccts.getAccounts()[i].holder.equals(account.holder) &&
                    ((allAccts.getAccounts()[i].getType().equals("Checking") &&
                    account.getType().equals("College Checking")) ||
                            (allAccts.getAccounts()[i].getType().equals("College Checking")
                                    && account.getType().equals("Checking")))) {
                System.out.println(account.holder.toString() + " same account(type) is in the database.");
                return true;
            }
        }
        return false;
    }


    /**
     Checks if the information needed to open an account is valid by checking if there is enough data, if the date of
     birth of the potential account holder is valid, if a valid amount of balance is given, and if the initial deposit
     is positive.
     @param splitInformation a string array containing the information obtained from standard input that is needed to
     open an account.
     @return true if the information given is valid, false if it is not valid.
     */
    private boolean validInformationChecker(String[] splitInformation) {
        if (splitInformation[1].equals("C") || splitInformation[1].equals("MM")) {
            if (splitInformation.length < VALID_NUMBER_OF_INFORMATION_FOR_OPENING_CHECKING_OR_MONEY_MARKET) {
                System.out.println("Missing data for opening an account.");
                return false;
            }
        } else if (splitInformation[1].equals("CC") || splitInformation[1].equals("S")) {
            if (splitInformation.length < VALID_NUMBER_OF_INFORMATION_FOR_OPENING_COLLEGE_CHECKING_OR_SAVINGS) {
                System.out.println("Missing data for opening an account.");
                return false;
            }
        }
        Date dob = new Date(splitInformation[4]);
        Date today = new Date();
        if (dob.isValid() == false || dob.compareTo(today) >= 0) {
            System.out.println("Date of birth invalid.");
            return false;
        }
        double balance = 0;
        try {
            balance = Double.parseDouble(splitInformation[5]);
        } catch (NumberFormatException invalidBalance) {
            System.out.println("Not a valid amount.");
            return false;
        }
        if (balance <= 0) {
            System.out.println("Initial deposit cannot be 0 or negative.");
            return false;
        }
        return true;
    }


    /**
     Checks if the balance that is being deposited or withdrew is a valid amount, as well as if the balance is positive.
     @param splitInformation a string array containing the information obtained from standard input that is needed to
     deposit into or withdraw from an account in the database.
     @return true if the deposited or withdrawn balance is valid, false otherwise.
     */
    private boolean depositAndWithdrawBalanceChecker(String[] splitInformation) {
        double balance = 0;
        try {
            balance = Double.parseDouble(splitInformation[5]);
        } catch (NumberFormatException invalidBalance) {
            System.out.println("Not a valid amount.");
            return false;
        }
        if (balance <= 0 && splitInformation[0].equals("D")) {
            System.out.println("Deposit - amount cannot be 0 or negative.");
            return false;
        } else if (balance <= 0 && splitInformation[0].equals("W")) {
            System.out.println("Withdraw - amount cannot be 0 or negative.");
            return false;
        }
        return true;
    }


    /**
     Checks if the number of accounts in the database is 0 or not, and if not, then it prints these accounts in one of
     many ways. It can print either by the order given, by account type, by fees and monthly interests based on the
     account types, or by first updating the balances based on fees and monthly interests and then printing using by the
     order given.
     @param splitInformation a string array containing information from standard input, and it contains one of the
     commands needed to print the accounts in the database.
     */
    private void checkNumberOfAccountsAndPrint(String[] splitInformation) {
        if (allAccts.getNumAcct() == 0) {
            System.out.println("Account Database is empty!");
            return;
        }
        if (splitInformation[0].equals("P")) {
            System.out.println("\n*list of accounts in the database*");
            allAccts.print();
            System.out.println("*end of list*\n");
        } else if (splitInformation[0].equals("PT")) {
            System.out.println("\n*list of accounts by account type.");
            allAccts.printByAccountType();
            System.out.println("*end of list.\n");
        } else if (splitInformation[0].equals("PI")) {
            System.out.println("\n*list of accounts with fee and monthly interest");
            allAccts.printFeeAndInterest();
            System.out.println("*end of list.\n");
        } else if (splitInformation[0].equals("UB")) {
            for (int i = 0; i < allAccts.getNumAcct(); i++) {
                allAccts.getAccounts()[i].updateBalance();
            }
            System.out.println("\n*list of accounts with updated balance");
            allAccts.print();
            System.out.println("*end of list.\n");
        }
    }


    /**
     Opens or reopens an account of a specific type as long as the information obtained is valid.
     @param splitInformation a string array containing the information obtained from standard input that is needed to
     open an account.
     */
    private void openAccount(String[] splitInformation) {
        if (!validInformationChecker(splitInformation)) {
            return;
        }
        Profile holder = new Profile(splitInformation[2], splitInformation[3], new Date(splitInformation[4]));
        Account addAccount = null;
        if (splitInformation[1].equals("C")) {
            addAccount = new Checking(holder, Double.parseDouble(splitInformation[5]));
        } else if (splitInformation[1].equals("CC")) {
            int campusCode = Integer.parseInt(splitInformation[6]);
            if (!(campusCode == NEW_BRUNSWICK_CAMPUS_CODE || campusCode == NEWARK_CAMPUS_CODE
                    || campusCode == CAMDEN_CAMPUS_CODE)) {
                System.out.println("Invalid campus code.");
                return;
            }
            addAccount = new CollegeChecking(holder, Double.parseDouble(splitInformation[5]), campusCode);
        } else if (splitInformation[1].equals("S")) {
            addAccount = new Savings(holder, Double.parseDouble(splitInformation[5]), Integer.parseInt(splitInformation[6]));
        } else if (splitInformation[1].equals("MM")) {
            addAccount = new MoneyMarket(holder, Double.parseDouble(splitInformation[5]));
            MoneyMarket testAccount = (MoneyMarket) addAccount;
            if (testAccount.balance < MONEY_MARKET_WAIVED_THRESHOLD && accountFinder(testAccount) == NOT_FOUND) {
                System.out.println("Minimum of $2500 to open a MoneyMarket account.");
                return;
            }
            if (testAccount.balance < MONEY_MARKET_WAIVED_THRESHOLD) {
                testAccount.loyalCustomer = false;
            }
        }
        if (sameAccountsChecker(addAccount)) {
            return;
        }
        if (!(allAccts.open(addAccount))) {
            System.out.println("Account reopened.");
        } else {
            System.out.println("Account opened.");
        }
    }


    /**
     Closes an account of a specific type as long as the information obtained is valid.
     @param splitInformation a string array containing the information obtained from standard input that is needed to
     close an account.
     */
    private void closeAccount(String[] splitInformation) {
        if (splitInformation.length < VALID_NUMBER_OF_INFORMATION_FOR_CLOSING_ACCOUNT) {
            System.out.println("Missing data for closing an account.");
            return;
        }
        Profile holder = new Profile(splitInformation[2], splitInformation[3], new Date(splitInformation[4]));
        Account closeAccount = null;
        if (splitInformation[1].equals("C")) {
            closeAccount = new Checking(holder, 0);
        } else if (splitInformation[1].equals("CC")) {
            closeAccount = new CollegeChecking(holder, 0, 0);
        } else if (splitInformation[1].equals("S")) {
            closeAccount = new Savings(holder, 0, 0);
        } else if (splitInformation[1].equals("MM")) {
            closeAccount = new MoneyMarket(holder, 0);
        }
        if (accountFinder(closeAccount) == NOT_FOUND) {
            System.out.println("Cannot close an account that doesn't exist.");
            return;
        }
        if (allAccts.getAccounts()[accountFinder(closeAccount)].closed == true) {
            System.out.println("Account is closed already.");
            return;
        }
        allAccts.close(closeAccount);
        System.out.println("Account closed.");
    }


    /**
     Deposits money into an account of a specific type in the database as long as the information obtained is valid.
     @param splitInformation a string array containing the information obtained from standard input that is needed to
     deposit money into an account in the database.
     */
    private void depositIntoAccount(String[] splitInformation) {
        if (!(depositAndWithdrawBalanceChecker(splitInformation))) {
            return;
        }
        Profile holder = new Profile(splitInformation[2], splitInformation[3], new Date(splitInformation[4]));
        Account increaseBalance = null;
        if (splitInformation[1].equals("C")) {
            increaseBalance = new Checking(holder, Double.parseDouble(splitInformation[5]));
        } else if (splitInformation[1].equals("CC")) {
            increaseBalance = new CollegeChecking(holder, Double.parseDouble(splitInformation[5]), 0);
        } else if (splitInformation[1].equals("S")) {
            increaseBalance = new Savings(holder, Double.parseDouble(splitInformation[5]), 0);
        } else if (splitInformation[1].equals("MM")) {
            increaseBalance = new MoneyMarket(holder, Double.parseDouble(splitInformation[5]));
        }
        int findMatchingAccountIndex = accountFinder(increaseBalance);
        if (findMatchingAccountIndex == NOT_FOUND && !(splitInformation[1].equals("MM"))) {
            System.out.println(increaseBalance.holder + " " + increaseBalance.getType() + " is not in the database.");
            return;
        } else if (findMatchingAccountIndex == NOT_FOUND && splitInformation[1].equals("MM")) {
            System.out.println(increaseBalance.holder + " Money Market is not in the database.");
            return;
        }
        allAccts.deposit(increaseBalance);
        System.out.println("Deposit - balance updated.");
    }


    /**
     Withdraws money from an account of a specific type in the database as long as the information obtained is valid.
     @param splitInformation a string array containing the information obtained from standard input that is needed to
     withdraw money from an account in the database.
     */
    private void withdrawFromAccount(String[] splitInformation) {
        if (!(depositAndWithdrawBalanceChecker(splitInformation))) {
            return;
        }
        Profile holder = new Profile(splitInformation[2], splitInformation[3], new Date(splitInformation[4]));
        Account decreaseBalance = null;
        if (splitInformation[1].equals("C")) {
            decreaseBalance = new Checking(holder, Double.parseDouble(splitInformation[5]));
        } else if (splitInformation[1].equals("CC")) {
            decreaseBalance = new CollegeChecking(holder, Double.parseDouble(splitInformation[5]), 0);
        } else if (splitInformation[1].equals("S")) {
            decreaseBalance = new Savings(holder, Double.parseDouble(splitInformation[5]), 0);
        } else if (splitInformation[1].equals("MM")) {
            decreaseBalance = new MoneyMarket(holder, Double.parseDouble(splitInformation[5]));
        }
        int findMatchingAccountIndex = accountFinder(decreaseBalance);
        if (findMatchingAccountIndex == NOT_FOUND && !(splitInformation[1].equals("MM"))) {
            System.out.println(decreaseBalance.holder + " " + decreaseBalance.getType() + " is not in the database.");
            return;
        } else if (findMatchingAccountIndex == NOT_FOUND && splitInformation[1].equals("MM")) {
            System.out.println(decreaseBalance.holder + " Money Market is not in the database.");
            return;
        }
        if (!(allAccts.withdraw(decreaseBalance))) {
            System.out.println("Withdraw - insufficient fund.");
            return;
        }
        System.out.println("Withdraw - balance updated.");
    }


    /**
     Reads in commands and information from standard input, and modifies the AccountDatabase object based on these
     instructions. If "O" is read followed by information, it will try to create, open, and add an account to the
     AccountDatabase object if the information is valid. If "C" is read followed by information, it will try to close
     an account. If "D" is read, it will try to deposit into an account. If "W" is read, it will try to withdraw from an
     account. If "P" is read, it will try to print the accounts in the AccountDatabase object in the order given. If
     "PT" is read, it will try to print by account type. If "PI" is read, it will try to print by fees and monthly
     interests based on the account types. If "UB" is read, it will update the balances for the accounts based on the
     fees and monthly interests, and then print in the order given. If "Q" is read, the program will come to an end.
     */
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
            } else if (splitInformation[0].equals("P") || splitInformation[0].equals("PT") ||
                    splitInformation[0].equals("PI") || splitInformation[0].equals("UB")) {
                checkNumberOfAccountsAndPrint(splitInformation);
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
