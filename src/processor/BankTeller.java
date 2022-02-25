package processor;
import java.util.Scanner;
import java.lang.NumberFormatException;
public class BankTeller {

    private AccountDatabase allAccts;

    public static final int VALID_NUMBER_OF_INFORMATION_FOR_CHECKING_OR_MONEYMARKET = 6;
    public static final int VALID_NUMBER_OF_INFORMATION_FOR_COLLEGECHECKING_OR_SAVINGS = 7;

    public BankTeller() {
        this.allAccts = new AccountDatabase();
    }

    private void updateAndDisplayBalances() {
        for (int i = 0; i < allAccts.getNumAcct(); i++) {
            allAccts.getAccounts()[i].updateBalance();
        }
        allAccts.print();
    }

    private void openAccount(String[] splitInformation) {
        if (splitInformation[1].equals("C") || splitInformation[1].equals("MM")) {
            if (splitInformation.length < VALID_NUMBER_OF_INFORMATION_FOR_CHECKING_OR_MONEYMARKET) {
                System.out.println("Missing data for opening an account.");
                return;
            }
        } else if (splitInformation[1].equals("CC") || splitInformation[1].equals("S")) {
            if (splitInformation.length < VALID_NUMBER_OF_INFORMATION_FOR_COLLEGECHECKING_OR_SAVINGS) {
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
            allAccts.open(addAccount);
        } else if (splitInformation[1].equals("CC")) {
            int campusCode = Integer.parseInt(splitInformation[6]);
            if (!(campusCode == 0 || campusCode == 1 || campusCode == 2)) {
                System.out.println("Invalid campus code.");
                return;
            }
            CollegeChecking addAccount = new CollegeChecking(holder, balance, campusCode);
            allAccts.open(addAccount);
        } else if (splitInformation[1].equals("S")) {
            int loyalCustomerCode = Integer.parseInt(splitInformation[6]);
            Savings addAccount = new Savings(holder, balance, loyalCustomerCode);
            allAccts.open(addAccount);
        } else if (splitInformation[1].equals("MM")) {
            if (balance < 2500) {
                System.out.println("Minimum of $2500 to open a MoneyMarket account.");
                return;
            }
            MoneyMarket addAccount = new MoneyMarket(holder, balance);
            allAccts.open(addAccount);
        } else {
            System.out.println("Invalid Command!");
            return;
        }
        System.out.println("Account opened.");
    }

    private void closeAccount(Scanner readStandardInput) {
        if (readStandardInput.next().equals("C")) {
            Checking addAccount = new Checking(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                    0);
            allAccts.close(addAccount);
        } else if (readStandardInput.next().equals("CC")) {
            CollegeChecking addAccount = new CollegeChecking(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                    0, 0);
            allAccts.close(addAccount);
        } else if (readStandardInput.next().equals("S")) {
            Savings addAccount = new Savings(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                    0, 0);
            allAccts.close(addAccount);
        } else if (readStandardInput.next().equals("MM")) {
            MoneyMarket addAccount = new MoneyMarket(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                    0);
            allAccts.close(addAccount);
        }
    }
    private void depositIntoAccount(Scanner readStandardInput) {
        String storeCommand = readStandardInput.next();
        Profile holder = new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next()));
        int balance = readStandardInput.nextInt();
        if (balance <= 0) {
            System.out.println("Deposit - amount cannot be 0 or negative.");
            return;
        }
        if (storeCommand.equals("C")) {
            Checking increaseBalance = new Checking(holder, balance);
            allAccts.deposit(increaseBalance);
        } else if (storeCommand.equals("CC")) {
            CollegeChecking increaseBalance = new CollegeChecking(holder, balance, 0);
            allAccts.deposit(increaseBalance);
        } else if (storeCommand.equals("S")) {
            Savings increaseBalance = new Savings(holder, balance, 0);
            allAccts.deposit(increaseBalance);
        } else if (storeCommand.equals("MM")) {
            MoneyMarket increaseBalance = new MoneyMarket(holder, balance);
            allAccts.deposit(increaseBalance);
        }
        System.out.println("Deposit - balance updated.");
    }

    private void withdrawFromAccount(Scanner readStandardInput) {
        String storeCommand = readStandardInput.next();
        Profile holder = new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next()));
        int balance = readStandardInput.nextInt();
        if (balance <= 0) {
            System.out.println("Withdraw - amount cannot be 0 or negative.");
            return;
        }
        if (storeCommand.equals("C")) {
            Checking increaseBalance = new Checking(holder, balance);
            allAccts.deposit(increaseBalance);
        } else if (storeCommand.equals("CC")) {
            CollegeChecking increaseBalance = new CollegeChecking(holder, balance, 0);
            allAccts.deposit(increaseBalance);
        } else if (storeCommand.equals("S")) {
            Savings increaseBalance = new Savings(holder, balance, 0);
            allAccts.deposit(increaseBalance);
        } else if (storeCommand.equals("MM")) {
            MoneyMarket increaseBalance = new MoneyMarket(holder, balance);
            allAccts.deposit(increaseBalance);
        }
        System.out.println("Withdraw - balance updated.");
    }
    public void run() {
        Scanner readStandardInput = new Scanner(System.in);
        while (readStandardInput.hasNext()) {
            String storeLine = readStandardInput.nextLine();
            String[] splitInformation = storeLine.split("\\s+");
            if (splitInformation[0].equals("O")) {
                openAccount(splitInformation);
            } else if (splitInformation[0].equals("C")) {
                closeAccount(readStandardInput);
            } else if (splitInformation[0].equals("D")) {
                depositIntoAccount(readStandardInput);
            } else if (splitInformation[0].equals("W")) {
                withdrawFromAccount(readStandardInput);
            } else if (splitInformation[0].equals("P")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    continue;
                }
                System.out.println("\n*list of accounts in the database*");
                allAccts.print();
                System.out.println("*end of list.\n");
            } else if (splitInformation[0].equals("PT")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    continue;
                }
                System.out.println("\n*list of accounts by account type.");
                allAccts.printByAccountType();
                System.out.println("*end of list.\n");
            } else if (splitInformation[0].equals("PI")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    continue;
                }
                System.out.println("\n*list of accounts with fee and monthly interest");
                allAccts.printFeeAndInterest();
                System.out.println("*end of list.\n");
            } else if (splitInformation[0].equals("UB")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    continue;
                }
                System.out.println("\n*list of accounts with updated balance");
                updateAndDisplayBalances();
                System.out.println("*end of list.\n");
            } else if (splitInformation[0].equals("Q")) {
                System.out.println("Bank Teller is terminated.");
                break;
            } else {
                System.out.println("Invalid Command!");
            }
        }
        readStandardInput.close();
    }

    public static void main(String[] args) {

    }
}
