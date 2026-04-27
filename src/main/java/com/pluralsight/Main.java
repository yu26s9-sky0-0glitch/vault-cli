package com.pluralsight;

import com.pluralsight.ui.Console;

public class Main {
    static void main(){
        String command;
        do{
            command = Console.promptForString("""
                    ------Home Screen------
                    D) Add Deposit
                    P) Make Payment (Debit)
                    L) Ledger
                    X) Exit""");
        switch (command.toUpperCase()){
            case "D":
              //  addDeposit();
                break;
            case "P":
              //  makePayment();
                break;
            case "L":
               // ledgerMenu();
                break;
            case "X":
                break;
            default:
                System.out.println("Invalid Input Try again!");
        }
        }while (!command.equalsIgnoreCase("X"));
        System.out.println("Session terminated. Vault locked. See you next time!");
    }
}
