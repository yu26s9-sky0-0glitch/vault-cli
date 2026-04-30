package com.pluralsight;
import com.pluralsight.ui.Console;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Ledger {
    static ArrayList<Transaction> ledger = getLedger();
    /**
     * Displays a menu and lets user choose between different ledger display options.
     * user can go back to home screen by pressing "H"
     */
    public static void ledgerMenu() {
        String command;
        do {
            command = Console.promptForString("""
                    <-<-<-<-Ledger->->->->
                    -> A) All
                    -> D) Deposit
                    -> P) Payments
                    -> R) Reports
                    -> H) Home
                    ->->->->->->->""");
            switch (command.toUpperCase()) {
                case "A":
                    displayAll();
                    break;
                case "D":
                    displayDeposit();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    Reports.reportsMenu();
                    break;
                case "H":
                    break;
                default:
                    System.out.println("Invalid Input! Try Again.");

            }
        } while (!command.equalsIgnoreCase("H"));
    }


    /**
     * Read all transactions and add it to Arraylist of transaction class
     * @return the Array list
     */
    public static ArrayList<Transaction> getLedger() {
        ArrayList<Transaction> ledgerLoader = new ArrayList<>();
        try {
            FileReader fr = new FileReader("data/transaction.csv");
            BufferedReader bfReader = new BufferedReader(fr);
            bfReader.readLine();
            String input;
            while ((input = bfReader.readLine()) != null) {
                String[] parts = input.split("\\|");
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                double amount = Double.parseDouble(parts[4]);
                ledgerLoader.add(new Transaction(date, time, parts[2], parts[3], amount));
            }
            bfReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ledgerLoader;
    }

    /**
     * sorts the arraylist based on date then time in a reversed order
     * @param ledgerLoader the ledger arraylist loaded with Transaction objects
     * @return sorted and reversed ledger
     */
    public static ArrayList<Transaction> sortLedger(ArrayList<Transaction> ledgerLoader) {

        ledgerLoader.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed()
        );
        return ledgerLoader;
    }

    /**
     * displays all transactions
     */
    private static void displayAll() {
        for (Transaction t : sortLedger(ledger)) System.out.println(t.toString());
    }

    /**
     * displays all the transaction with positive amount aka deposits
     */
    private static void displayDeposit() {
        for (Transaction t : sortLedger(ledger)) {
            if (t.getAmount() > 0) {
                System.out.println(t);
            }
        }
    }

    /**
     * displays all the transaction with negative amount aka Payments
     */
    private static void displayPayments() {
        for (Transaction t : sortLedger(ledger)) {
            if (t.getAmount() < 0) {
                System.out.println(t);
            }
        }

    }

}

