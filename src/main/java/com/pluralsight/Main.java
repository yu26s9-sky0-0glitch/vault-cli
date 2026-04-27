package com.pluralsight;
import com.pluralsight.ui.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    static void main(){
        String command;
        do{
            command = Console.promptForString("""
                    <-<-<-<-Home Screen->->->->
                    -> D) Add Deposit
                    -> P) Make Payment (Debit)
                    -> L) Ledger
                    -> X) Exit
                    >->->->->->->""");
        switch (command.toUpperCase()){
            case "D":
                addTransaction(1);
                break;
            case "P":
               addTransaction(-1);
                break;
            case "L":
               ledgerMenu();
                break;
            case "X":
                break;
            default:
                System.out.println("Invalid Input Try again!");
        }
        }while (!command.equalsIgnoreCase("X"));
        System.out.println("Session terminated. Vault locked. See you next time!");
    }


    /**
     * Prompt the user for description,Vendor and Amount gets the current time and date
     * formats the time to hh:mm:ss
     * @param sign takes 1 or -1 as parameter and multiplies the amount to it.
     * calls writeToLedger with all variables defined
     */
    private static void addTransaction(int sign) {
        String description = Console.promptForString("Briefly describe the transaction:");
        String payer = Console.promptForString("Enter the business or person involved: ");
        double amount = Console.promptForDouble("Enter the total amount Transacted: ");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm:ss");
        String formattedTime = time.format(fmt);
        writeToLedger(date,formattedTime,description,payer,Math.abs(amount)*sign);
    }

    /**
     * Opens the transaction.csv write into it the data passed as parameter in appropriate format.
     * @param date the date of transaction
     * @param formattedTime the time of transaction in hh:mmm:ss format
     * @param description brief description of the transaction
     * @param payer name of the person/vendor/business that made the deposit
     * @param amount amount paid
     */
    private static void writeToLedger(LocalDate date, String formattedTime, String description, String payer, double amount) {
        try {
            FileWriter fr = new FileWriter("data/transaction.csv", true);
            String line = String.format("%s|%s|%s|%s|%.2f\n",date,formattedTime,description,payer,amount);
            fr.write(line);
            fr.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        System.out.println("---------------------------------------------");
        System.out.println("Entry synchronized. Ledger integrity maintained.");
        System.out.println("---------------------------------------------");
    }

    /**
     * Displays a menu and lets user choose between different ledger display options.
     * user can go back to home screen by pressing "H"
     */
    private static void ledgerMenu() {
        String command;
        do{
            command = Console.promptForString("""
                    <-<-<-<-Ledger->->->->
                    -> A) All
                    -> D) Deposit
                    -> P) Payments
                    -> R) Reports
                    -> H) Home
                    ->->->->->->->""");
            switch (command.toUpperCase()){
                case "A":
                    //displayAll()
                    break;
                case "D":
                    //displayDeposit()
                    break;
                case "P":
                    //displayPayments()
                    break;
                case "R":
                    //reportsMenu()
                    break;
                case "H":
                    break;
                default:
                    System.out.println("Invalid Input! Try Again.");

            }
        }while (!command.equalsIgnoreCase("H"));
    }
}
