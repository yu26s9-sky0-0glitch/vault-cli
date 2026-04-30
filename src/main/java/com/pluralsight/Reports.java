package com.pluralsight;
import com.pluralsight.ui.Console;
import java.time.LocalDate;
import java.util.ArrayList;

public class Reports {
    static ArrayList<Transaction> ledger = Ledger.getLedger();

    /**
     * displays the reports Menu which gives user different option for filtering
     */
    public static void reportsMenu() {
        int command;
        do{
            command = Console.promptForInt("""
                    <-<-<-<-Reports->->->->
                    -> 1) Month To Date
                    -> 2) Previous Month
                    -> 3) Year To Date
                    -> 4) Previous Year
                    -> 5) Search by Vendor
                    -> 0) Back
                   ->->->->->->""",0,5);
            switch (command){
                case 1:
                    monthToDate();
                    break;
                case 2:
                    previousMonth();
                    break;
                case 3:
                    yearToDate();
                    break;
                case 4:
                    previousYear();
                    break;
                case 5:
                    searchByVendor();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid Input! Try Again.");
            }
        }while (command!=0);
    }


    /**
     *displays all month to date transactions
     */
    private static void monthToDate() {
        int monthValue = LocalDate.now().getMonthValue();
        int yearValue = LocalDate.now().getYear();
        for(Transaction t:Ledger.sortLedger(ledger)){
            if (t.getDate().getMonthValue()==monthValue && t.getDate().getYear()==yearValue){
                System.out.println(t);
            }
        }
    }
    /**
     * displays all transaction in past month
     */
    private static void previousMonth() {
        int monthValue = LocalDate.now().getMonthValue();
        int yearValue = LocalDate.now().getYear();
        for(Transaction t:Ledger.sortLedger(ledger)){
            if (t.getDate().getMonthValue()==monthValue-1 && t.getDate().getYear()==yearValue){
                System.out.println(t);
            }
        }
    }
    /**
     *displays all year to date transactions
     */
    private static void yearToDate() {
        int yearValue = LocalDate.now().getYear();
        for(Transaction t:Ledger.sortLedger(ledger)){
            if (t.getDate().getYear()==yearValue){
                System.out.println(t);
            }
        }
    }
    /**
     * displays all transaction in past year
     */
    private static void previousYear() {
        int yearValue = LocalDate.now().getYear();
        for(Transaction t:Ledger.sortLedger(ledger)){
            if (t.getDate().getYear()==yearValue-1){
                System.out.println(t);
            }
        }
    }
    /**
     * displays all transaction in specified
     */
    private static void searchByVendor() {
        String vendor = Console.promptForString("Enter the vendor: ");
        for(Transaction t : Ledger.sortLedger(ledger)){
            if (vendor.equalsIgnoreCase(t.getVendor())){
                System.out.println(t);
            }
        }
    }

}
