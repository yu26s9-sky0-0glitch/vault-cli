package com.pluralsight;
import com.pluralsight.ui.Console;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
                    -> 6) Custom Search
                    -> 0) Back
                   ->->->->->->""",0,6);
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
                case 6:
                    customSearch();
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

    /**
     * prompts the user for filter fields
     * displays the matched result or outputs no match found
     */
    private static void customSearch() {
        boolean found = false;
        LocalDate startDate = Console.promptForOptionalDate("Start Date (yyyy-mm-dd) or Enter to skip: ");
        LocalDate endDate = Console.promptForOptionalDate("End Date (yyyy-mm-dd) or Enter to skip: ");
        String description = Console.promptForStringEmpty("Description or Enter to skip: ");
        String vendor = Console.promptForStringEmpty("Vendor or Enter to skip: ");
        String amountInput = Console.promptForStringEmpty("Amount or Enter to skip: ");
        for(Transaction t : Ledger.sortLedger(ledger)){
            if(filterPassed(t,startDate,endDate,description,vendor,amountInput)){
                System.out.println(t);
                found = true;
            }
        }
            if(!found){
                System.out.println("No Match Found!");
            }
    }


    /**
     * Evaluates a transaction against multiple optional search criteria
     * Filters are ignored if the corresponding parameter is null or empty
     *  @param t           The transaction to match
     * @param startDate   Minimum date Skipped if null
     * @param endDate     Maximum date Skipped if null
     * @param description Text search for description Skipped if empty
     * @param vendor      Text search for vendor Skipped if empty
     * @param amountInput Exact amount search Skipped if null
     * @return            True if the transaction matches all active filters
     */
    private static boolean filterPassed(Transaction t, LocalDate startDate,
                                        LocalDate endDate, String description,
                                        String vendor, String amountInput) {
        if(startDate != null  && t.getDate().isBefore(startDate) )return false;
        if(endDate!=null && t.getDate().isAfter(endDate))return false;
        if(!description.isEmpty() && !t.getDescription().equalsIgnoreCase(description))return false;
        if(!vendor.isEmpty() && !t.getVendor().equalsIgnoreCase(vendor))return false;
        if(!amountInput.isEmpty() && t.getAmount() != Double.parseDouble(amountInput))return false;
        return true;
    }
}
