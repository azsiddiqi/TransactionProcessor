package processor;
import java.util.Scanner;
import java.lang.NumberFormatException;

public class BankTeller {

    private AccountDatabase allAccts;

    public static final int VALID_NUMBER_OF_INFORMATION_FOR_OPENING_CHECKING_OR_MONEY_MARKET = 6;
    public static final int VALID_NUMBER_OF_INFORMATION_FOR_OPENING_COLLEGE_CHECKING_OR_SAVINGS = 7;
    public static final int VALID_NUMBER_OF_INFORMATION_FOR_CLOSING_ACCOUNT = 5;
    public static final int NOT_FOUND = -1;

    public BankTeller() {
        this.allAccts = new AccountDatabase();
    }

    private int accountFinder(Account account) {
        for (int i = 0; i < allAccts.getNumAcct(); i++) {
            if (allAccts.getAccounts()[i].equals(account)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

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
            if (!(campusCode == 0 || campusCode == 1 || campusCode == 2)) {
                System.out.println("Invalid campus code.");
                return;
            }
            addAccount = new CollegeChecking(holder, Double.parseDouble(splitInformation[5]), campusCode);
        } else if (splitInformation[1].equals("S")) {
            int loyalCustomerCode = Integer.parseInt(splitInformation[6]);
            addAccount = new Savings(holder, Double.parseDouble(splitInformation[5]), loyalCustomerCode);
        } else if (splitInformation[1].equals("MM")) {
            addAccount = new MoneyMarket(holder, Double.parseDouble(splitInformation[5]));
            MoneyMarket testAccount = (MoneyMarket) addAccount;
            if (testAccount.balance < 2500 && accountFinder(testAccount) == NOT_FOUND) {
                System.out.println("Minimum of $2500 to open a MoneyMarket account.");
                return;
            }
            if (testAccount.balance < 2500) {
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
        if (allAccts.getAccounts()[accountFinder(closeAccount)].closed == true) {
            System.out.println("Account is closed already.");
            return;
        }
        allAccts.close(closeAccount);
        System.out.println("Account closed.");
    }

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
        if (decreaseBalance.balance > allAccts.getAccounts()[accountFinder(decreaseBalance)].balance) {
            System.out.println("Withdraw - insufficient fund.");
            return;
        }
        allAccts.withdraw(decreaseBalance);
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
