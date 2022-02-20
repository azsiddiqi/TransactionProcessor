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

    public void run() {
        Scanner readStandardInput = new Scanner(System.in);
        while (readStandardInput.hasNext()) {
            String storeCommand = readStandardInput.next();
            if (storeCommand.equals("O")) {

            } else if (storeCommand.equals("C")) {

            } else if (storeCommand.equals("D")) {

            } else if (storeCommand.equals("W")) {

            } else if (storeCommand.equals("P")) {
                System.out.println("\n*list of accounts in the database*");
                allAccts.print();
                System.out.println("*end of list.\n");
            } else if (storeCommand.equals("PT")) {
                System.out.println("\n*list of accounts by account type.");
                allAccts.printByAccountType();
                System.out.println("*end of list.\n");
            } else if (storeCommand.equals("PI")) {
                System.out.println("\n*list of accounts with fee and monthly interest");
                allAccts.printFeeAndInterest();
                System.out.println("*end of list.\n");
            } else if (storeCommand.equals("UB")) {
                System.out.println("\n*list of accounts with updated balance");
                updateAndDisplayBalances();
                System.out.println("*end of list.\n");
            } else if (storeCommand.equals("Q")) {
                System.out.println("Bank Teller is terminated.");
                break;
            } else {
                System.out.println("error");
            }
        }
        readStandardInput.close();
    }
}
