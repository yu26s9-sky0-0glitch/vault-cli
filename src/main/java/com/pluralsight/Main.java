package com.pluralsight;
import com.pluralsight.ui.Console;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    static void main() {
        String command;
        do {
            command = Console.promptForString("""
                    <-<-<-<-Home Screen->->->->
                    -> D) Add Deposit
                    -> P) Make Payment (Debit)
                    -> L) Ledger
                    -> X) Exit
                    >->->->->->->""");
            switch (command.toUpperCase()) {
                case "D":
                    addTransaction("Deposit");
                    break;
                case "P":
                    addTransaction("Payment");
                    break;
                case "L":
                    Ledger.ledgerMenu();
                    break;
                case "X":
                    break;
                default:
                    System.out.println("Invalid Input Try again!");
                    break;
            }
        } while (!command.equalsIgnoreCase("X"));
        System.out.println("Session terminated. Vault locked. See you next time!");
    }


    /**
     * Prompt the user for description,Vendor and Amount gets the current time and date
     * formats the time to hh:mm:ss
     * @param operation should be "Deposit" or "Payment"
     * calls writeToLedgerCsv with all variables defined
     */
    private static void addTransaction(String operation) {
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = time.format(fmt);
        if (operation.equalsIgnoreCase("Deposit")) {
            String description = Console.promptForString("Briefly describe the deposit purpose: ");
            String payer = Console.promptForString("Enter the business or person involved: ");
            double amount = Console.promptForDouble("Enter the total amount deposited(No $ sign): ");
            writeToLedgerCsv(date, LocalTime.parse(formattedTime), description, payer, Math.abs(amount));
        }
        else if (operation.equalsIgnoreCase("Payment")) {
            String description = Console.promptForString("Briefly describe the payment purpose: ");
            String payer = Console.promptForString("Enter the business or person involved: ");
            double amount = Console.promptForDouble("Enter the total amount paid(No $ sign): ");
            writeToLedgerCsv(date,LocalTime.parse(formattedTime),description, payer,Math.abs(amount) * -1);
        }
    }

    /**
     * Opens the transaction.csv writes into it the data passed as parameter in appropriate format.
     * @param date          the date of transaction
     * @param formattedTime the time of transaction in hh:mmm:ss format
     * @param description   brief description of the transaction
     * @param payer         name of the person/vendor/business that made the deposit
     * @param amount        amount paid
     */
    private static void writeToLedgerCsv(LocalDate date, LocalTime formattedTime, String description, String payer, double amount) {

        try {
            FileWriter fr = new FileWriter("data/transaction.csv", true);
            BufferedWriter bfwriter = new BufferedWriter(fr);
            String line = String.format("%s|%s|%s|%s|%.2f\n", date, formattedTime, description, payer, amount);
            bfwriter.write(line);
            bfwriter.close();
            fr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("---------------------------------------------");
        System.out.println("Entry synchronized. Ledger integrity maintained.");
        System.out.println("---------------------------------------------");
    }

}