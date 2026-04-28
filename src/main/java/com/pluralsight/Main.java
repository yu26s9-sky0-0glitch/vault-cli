package com.pluralsight;
import com.pluralsight.ui.Console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {
    static ArrayList<Transaction> ledger = getLedger();


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
                    displayAll();
                    break;
                case "D":
                    displayDeposit();
                    break;
                case "P":
                    displayPayments();
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



    /**
     * Read all transactions and add it to Arraylist of transaction class
     * adds each new item to index zero pushing older dates down and reversing the order
     * @return the Array list
     */
    private static ArrayList<Transaction> getLedger() {
        ArrayList<Transaction> ledgerLoader = new ArrayList<>();
        try{
        FileReader fr = new FileReader("data/transaction.csv");
            BufferedReader bfReader = new BufferedReader(fr);
            bfReader.readLine();
            String input;
            while((input = bfReader.readLine())!=null){
                String[] parts = input.split("\\|");
                ledgerLoader.add(0,new Transaction(LocalDate.parse(parts[0]),LocalTime.parse(parts[1]),parts[2],parts[3],Double.parseDouble(parts[4])));
            }
            bfReader.close();
            System.out.println(ledgerLoader.getFirst().getAmount());
         }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return ledgerLoader;
    }

    /**
     * displays all transactions
     */
    private static void displayAll() {
        for(Transaction t:ledger){
            System.out.println(t.toString());
        }
    }

    /**
     * displays all the transaction with positive amount aka deposits
     */
    private static void displayDeposit() {
        for(Transaction t:ledger){
            if (t.getAmount()>0){
                System.out.println(t.toString());
            }
        }
    }

    /**
     * displays all the transaction with negative amount aka Payments
     */
    private static void displayPayments() {
        for(Transaction t:ledger){
            if (t.getAmount()<0){
                System.out.println(t.toString());
            }
        }

    }
}
