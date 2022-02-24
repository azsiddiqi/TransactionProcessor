package processor;
import java.util.Scanner;

public class BankTeller {

    private AccountDatabase allAccts;

    private void updateAndDisplayBalances() {
        for (int i = 0; i < allAccts.getNumAcct(); i++) {
            allAccts.getAccounts()[i].updateBalance();
        }
        allAccts.print();
    }

    private void openAccount(Scanner readStandardInput) {
        String storeCommand = readStandardInput.next();
        String fname = readStandardInput.next();
        String lname = readStandardInput.next();
        Date dob = new Date(readStandardInput.next());
        Date today = new Date();
        if (dob.isValid() == false || dob.compareTo(today) >= 0) {
            System.out.println("Date of birth invalid.");
            return;
        }
        Profile holder = new Profile(fname, lname, dob);
        int balance = readStandardInput.nextInt();
        if (balance <= 0) {
            System.out.println("Initial deposit cannot be 0 or negative.");
            return;
        }
        if (storeCommand.equals("C")) {
            Checking addAccount = new Checking(holder, balance);
            allAccts.open(addAccount);
        } else if (storeCommand.equals("CC")) {
            int campusCode = readStandardInput.nextInt();
            if (campusCode != 0 || campusCode != 1 || campusCode != 2) {
                System.out.println("Invalid campus code.");
                return;
            }
            CollegeChecking addAccount = new CollegeChecking(holder, balance, campusCode);
            allAccts.open(addAccount);
        } else if (storeCommand.equals("S")) {
            int loyalCustomerCode = readStandardInput.nextInt();
            Savings addAccount = new Savings(holder, balance, loyalCustomerCode);
            allAccts.open(addAccount);
        } else if (storeCommand.equals("MM")) {
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
            String storeCommand = readStandardInput.next();
            if (storeCommand.equals("O")) {
                openAccount(readStandardInput);
            } else if (storeCommand.equals("C")) {
                closeAccount(readStandardInput);
            } else if (storeCommand.equals("D")) {
                depositIntoAccount(readStandardInput);
            } else if (storeCommand.equals("W")) {
                withdrawFromAccount(readStandardInput);
            } else if (storeCommand.equals("P")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    return;
                }
                System.out.println("\n*list of accounts in the database*");
                allAccts.print();
                System.out.println("*end of list.\n");
            } else if (storeCommand.equals("PT")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    return;
                }
                System.out.println("\n*list of accounts by account type.");
                allAccts.printByAccountType();
                System.out.println("*end of list.\n");
            } else if (storeCommand.equals("PI")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    return;
                }
                System.out.println("\n*list of accounts with fee and monthly interest");
                allAccts.printFeeAndInterest();
                System.out.println("*end of list.\n");
            } else if (storeCommand.equals("UB")) {
                if (allAccts.getNumAcct() == 0){
                    System.out.println("Account Database is empty!");
                    return;
                }
                System.out.println("\n*list of accounts with updated balance");
                updateAndDisplayBalances();
                System.out.println("*end of list.\n");
            } else if (storeCommand.equals("Q")) {
                System.out.println("Bank Teller is terminated.");
                break;
            } else {
                System.out.println("Invalid Command!");
            }
        }
        readStandardInput.close();
    }
}
