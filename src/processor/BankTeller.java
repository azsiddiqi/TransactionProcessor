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
        Profile holder = new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next()));
        int balance = readStandardInput.nextInt();

        if (storeCommand.equals("C")) {
            Checking addAccount = new Checking(holder, balance);
            allAccts.open(addAccount);
        } else if (storeCommand.equals("CC")) {
            int campusCode = readStandardInput.nextInt();
            CollegeChecking addAccount = new CollegeChecking(holder, balance, campusCode);
            allAccts.open(addAccount);
        } else if (storeCommand.equals("S")) {
            int loyalCustomerCode = readStandardInput.nextInt();
            Savings addAccount = new Savings(holder, balance, loyalCustomerCode);
            allAccts.open(addAccount);
        } else if (storeCommand.equals("MM")) {
            MoneyMarket addAccount = new MoneyMarket(holder, balance);
            allAccts.open(addAccount);
        } else {
            System.out.println("Invalid Command!");
            return;
        }
    }
    public void run() {
        Scanner readStandardInput = new Scanner(System.in);
        while (readStandardInput.hasNext()) {
            String storeCommand = readStandardInput.next();
            if (storeCommand.equals("O")) {
                openAccount(readStandardInput);
            } else if (storeCommand.equals("C")) {
                if (readStandardInput.next().equals("C")) {
                    Checking addAccount = new Checking(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            0);
                    allAccts.open(addAccount);
                } else if (readStandardInput.next().equals("CC")) {
                    CollegeChecking addAccount = new CollegeChecking(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            0, 0);
                } else if (readStandardInput.next().equals("S")) {
                    Savings addAccount = new Savings(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            0, 0);
                } else if (readStandardInput.next().equals("MM")) {
                    MoneyMarket addAccount = new MoneyMarket(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            0);
                }
            } else if (storeCommand.equals("D")) {
                if (readStandardInput.next().equals("C")) {
                    Checking addAccount = new Checking(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            readStandardInput.nextInt());
                    allAccts.open(addAccount);
                } else if (readStandardInput.next().equals("CC")) {
                    CollegeChecking addAccount = new CollegeChecking(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            readStandardInput.nextInt(), 0);
                } else if (readStandardInput.next().equals("S")) {
                    Savings addAccount = new Savings(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            readStandardInput.nextInt(), 0);
                } else if (readStandardInput.next().equals("MM")) {
                    MoneyMarket addAccount = new MoneyMarket(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            readStandardInput.nextInt());
                }
            } else if (storeCommand.equals("W")) {
                if (readStandardInput.next().equals("C")) {
                    Checking addAccount = new Checking(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            readStandardInput.nextInt());
                    allAccts.open(addAccount);
                } else if (readStandardInput.next().equals("CC")) {
                    CollegeChecking addAccount = new CollegeChecking(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            readStandardInput.nextInt(), 0);
                } else if (readStandardInput.next().equals("S")) {
                    Savings addAccount = new Savings(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            readStandardInput.nextInt(), 0);
                } else if (readStandardInput.next().equals("MM")) {
                    MoneyMarket addAccount = new MoneyMarket(new Profile(readStandardInput.next(), readStandardInput.next(), new Date(readStandardInput.next())),
                            readStandardInput.nextInt());
                }
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
