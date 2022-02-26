package processor;


/**
 This class is a driver class that runs the entire project.
 It is what allows the opening, closing, depositing, withdrawing, and printing of accounts to occur.
 Without this class, the project wouldn't be able to function.
 @author Karan Patel, Azaan Siddiqi
 */
public class RunProject2 {


    /**
     * Creates a BankTeller object and calls the run method inside the BankTeller class to start the project.
     * @param args Array to store the java command line arguments.
     */
    public static void main(String[] args) {
        new BankTeller().run();
    }

}
